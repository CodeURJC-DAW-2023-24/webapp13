package es.gualapop.backend.controller;

import es.gualapop.backend.model.User;
import es.gualapop.backend.model.Product;
import es.gualapop.backend.model.ProductDto;
import es.gualapop.backend.model.ProductsResponse;
import es.gualapop.backend.repository.ProductRepository;
import es.gualapop.backend.repository.ProductTypeRepository;
import es.gualapop.backend.repository.UserRepository;
import es.gualapop.backend.service.PDFService;
import es.gualapop.backend.service.ProductService;
import es.gualapop.backend.service.SearchService;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URI;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.DocumentException;


@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SearchService searchService;
    @Autowired
    private ProductTypeRepository productTypeRepository;
    @Autowired
    private PDFService pdfService;


    @GetMapping("/getProducts")
    public ResponseEntity<Page<ProductDto>> getProducts(@RequestParam int page, @RequestParam int pageSize) {
        try {
            Pageable pageable = PageRequest.of(page, pageSize);
            Page<Product> productsPage = productRepository.findAll(pageable);

            // Convertir imágenes a representación base64
            List<ProductDto> productDtos = productsPage.getContent().stream()
                    .map(product -> new ProductDto(
                            product.getId(),
                            product.getTitle(),
                            product.getAddress(),
                            product.getPrice(),
                            product.getDescription(),
                            convertBlobToBase64(product.getImageFile()),
                            product.isImage(),
                            product.getOwner(),
                            product.getProductType()))
                    .collect(Collectors.toList());

            Page<ProductDto> productDtoPage = new PageImpl<>(productDtos, pageable, productsPage.getTotalElements());
            return ResponseEntity.ok(productDtoPage);
        } catch (Exception e) {
            // Loguear la excepción
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Utilidad para convertir Blob a base64
    private String convertBlobToBase64(Blob blob) {
        try {
            if (blob != null) {
                byte[] bytes = blob.getBytes(1, (int) blob.length());
                return Base64.getEncoder().encodeToString(bytes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/product/{id}")
    public String getProduct(HttpServletRequest request, Model model, @PathVariable("id") Long id, @RequestParam(defaultValue = "0") int page) {
        model.addAttribute("categories", productTypeRepository.findAll());

        Product p = productService.getProductById(id);
        Optional<User> userOptional = userRepository.findById(p.getOwner());
        User u = userOptional.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("user", u);
        model.addAttribute("product", p);
        int pageSize = 4;

        // Verificar si el usuario está autenticado
        if (request.getUserPrincipal() != null) {
            String name = request.getUserPrincipal().getName();
            User user = userRepository.findUserByUsername(name).orElseThrow();
            model.addAttribute("admin", request.isUserInRole("ADMIN"));
        } else {
            // Manejar el caso cuando el usuario no está autenticado
            model.addAttribute("admin", false);
        }

        model.addAttribute("recommendations", true);
        model.addAttribute("recommendations", productService.getSimilarProducts(id, page, pageSize));
        return "productoIndividual";
    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<Object> downloadProductImage(@PathVariable long id) throws SQLException {

        Optional<Product> product = productService.findById(id);
        if (product.isPresent() && product.get().getImageFile() != null) {

            Resource file = new InputStreamResource(product.get().getImageFile().getBinaryStream());

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpg")
                    .contentLength(product.get().getImageFile().length()).body(file);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/product/category/{id}")
    public String redirectCategories(@PathVariable long id, Model model) {

        model.addAttribute("products", productRepository.findProductsByProductType(id));
        model.addAttribute("categories", productTypeRepository.findAll());
        return "index";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam("query") String query, Model model) {
        model.addAttribute("categories", productTypeRepository.findAll());
        model.addAttribute("products", searchService.searchProducts(query));
        return "index";
    }

    /*
    @GetMapping("/checkout/{productID}")
    public String checkout(Model model, @PathVariable("productID") Long productID, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 4;
        model.addAttribute("price", productService.getPriceByIdProduct(productID));
        model.addAttribute("product", true);

        // Recomendations
        Optional<Product> p = productRepository.findById(productID);

        model.addAttribute("recommendations", true);
        model.addAttribute("recommendations", productService.getSimilarProducts(p.map(Product::getProductType).orElse((long)0), page, pageSize));
        return "checkout";
    }
    */

    @GetMapping("/checkout/{productID}")
    public String checkout(Model model, @PathVariable("productID") Long productID, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 4;
        Optional<Product> productOptional = productRepository.findById(productID);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            model.addAttribute("price", productService.getPriceByIdProduct(productID));
            model.addAttribute("product", product);
            model.addAttribute("recommendations", productService.getSimilarProducts(product.getProductType(), page, pageSize));
            return "checkout";
        } else {
            return "error";
        }
    }

    @PostMapping("/purchase/{productID}")
    public ResponseEntity<byte[]> purchase(@PathVariable("productID") Long productID, Model model) throws SQLException, IOException, DocumentException {
        Optional<Product> products = productRepository.findById(productID);
        if (products.isPresent()){
            Product product = products.get();
            Double price = product.getPrice();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = authentication.getName();

            // Set earnings to seller
            Optional<User> vendedor = userRepository.findById(product.getOwner());
            if (vendedor.isPresent()){
                User user = vendedor.get();
                user.setIngresos(user.getIngresos()+price);
                userRepository.save(user);
            }

            // Set bills to purchaser
            Optional<User> comprador = userRepository.findByUsername(currentUsername);
            if (comprador.isPresent()){
                User usuario = comprador.get();
                usuario.setGastos(usuario.getGastos()+price);
                userRepository.save(usuario);
            }

            // Generate PDF
            byte[] pdfBytes = pdfService.generatePDF(product);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "purchaser_bill.pdf");
            headers.add("Refresh", "3;url=https://localhost:8443/");


            productRepository.deleteById(productID);
            
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } else {
            // Manejar el caso donde el producto no se encuentra
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/checkout/undefined")
    public String redireccionarAIndex() {
        
        return "redirect:/";
    }

    @GetMapping("/newProduct")
    public String newProducts(Model model) {

        return "newProduct";
    }


    @PostMapping("/addNewProduct")
    public String addNewProduct(Model model,
                                @RequestParam("Title") String title,
                                @RequestParam("Description") String description,
                                @RequestParam("Category") String category,
                                @RequestParam("Address") String Address,
                                @RequestParam("City") String City,
                                @RequestParam("Province") String Province,
                                @RequestParam("imageFile") MultipartFile imageFile,
                                @RequestParam("Cp") String Cp) throws IOException {
        //crear nuevo objeto product y settear atributows

        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        switch (category) {
            case "Electrónica":
                product.setProductType(1L);
                break;
            case "Muebles":
                product.setProductType(2L);
                break;
            case "Ropa":
                product.setProductType(3L);
                break;
            case "Libros":
                product.setProductType(4L);
                break;
            case "Deportes":
                product.setProductType(5L);
                break;
            case "Hogar y Jardín":
                product.setProductType(6L);
                break;
            case "Juguetes":
                product.setProductType(7L);
                break;
            default:
                // Manejar cualquier otro caso si es necesario
                break;
        }
        String fullAddress = Address + ", " + City + ", " + Province + ", " + Cp;
        product.setAddress(fullAddress);
        if (!imageFile.isEmpty()) {
            product.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
            product.setImage(true);
        }

        //save con el repository
        productRepository.save(product);

        return "redirect:/index";
    }

    /*
    public String generatePDF(@PathVariable long id) throws SQLException {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            pdfService.generatePDF(product.get());

            return "index";
        } else {
            System.out.println("Error creacion PDF");
            return "index";
        }
    }
    */

}
