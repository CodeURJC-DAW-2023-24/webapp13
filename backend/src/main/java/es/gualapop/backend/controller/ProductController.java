package es.gualapop.backend.controller;

import es.gualapop.backend.model.User;
import es.gualapop.backend.model.Product;
import es.gualapop.backend.model.DTO.ProductDto;
import es.gualapop.backend.model.Review;
import es.gualapop.backend.repository.ProductRepository;
import es.gualapop.backend.repository.ProductTypeRepository;
import es.gualapop.backend.repository.ReviewRepository;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
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
    private ReviewRepository reviewRepository;
    @Autowired
    private PDFService pdfService;




    @GetMapping("/getProducts")
    public String getProducts(Model model, HttpServletRequest request, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Product> productsPage = productRepository.findAll(pageable);

        model.addAttribute("products", productsPage.getContent());

        return "productIndex";
    }
    @GetMapping("/product/{id}")
    public String getProduct(HttpServletRequest request, Model model, @PathVariable("id") Long id, @RequestParam(defaultValue = "0") int page) {
        model.addAttribute("categories", productTypeRepository.findAll());

        Product p = productService.getProductById(id);
        Optional<User> userOptional = userRepository.findById(p.getOwner());
        User u = userOptional.orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", u);
        model.addAttribute("product", p);
        int pageSize = 4;

        // Check if user is logged
        if (request.getUserPrincipal() != null) {
            String name = request.getUserPrincipal().getName();
            userRepository.findUserByUsername(name).orElseThrow();
            //model.addAttribute("admin", request.isUserInRole("ADMIN"));
        } else {
            // If user is not logged
            model.addAttribute("admin", false);
        }

        model.addAttribute("recommendations", true);
        model.addAttribute("recommendations", productService.getSimilarProducts(id, page, pageSize));
        return "individualProduct";
    }

    @GetMapping("/product/{id}/delete")
    public String deleteProduct(HttpServletRequest request, Model model, @PathVariable("id") Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()){
            Product thisProduct = product.get();
            productRepository.deleteById(thisProduct.getId());
        }
        return "redirect:/profile";
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
    public String redirectCategories(@PathVariable long id,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "8") int pageSize,
                                     Model model) {

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Product> productsPage = productRepository.findProductsByProductType(id, pageable);

        model.addAttribute("products", productsPage.getContent());
        model.addAttribute("categories", productTypeRepository.findAll());
        return "productIndex";
    }

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
    public ResponseEntity<byte[]> purchase(@PathVariable("productID") Long productID, @RequestParam float rating, Model model) throws SQLException, IOException, DocumentException {
        Optional<Product> product = productRepository.findById(productID);
        if (product.isPresent()){
            Product product1 = product.get();
            Long sellerID = product1.getOwner();
            if (rating > 0){
                Review review = new Review(rating, sellerID);
                reviewRepository.save(review);
            }
            Double price = product1.getPrice();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = authentication.getName();

            // Set earnings to seller
            Optional<User> seller = userRepository.findById(product1.getOwner());
            if (seller.isPresent()){
                User user = seller.get();
                user.setIncome(user.getIncome()+price);
                userRepository.save(user);
            }

            // Set bills to purchaser
            Optional<User> buyer = userRepository.findByUsername(currentUsername);
            if (buyer.isPresent()){
                User thisUser = buyer.get();
                thisUser.setExpense(thisUser.getExpense()+price);
                userRepository.save(thisUser);
            }

            // Generate PDF
            byte[] pdfBytes = pdfService.generatePDF(product1);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "purchaser_bill.pdf");
            headers.add("Refresh", "3;url=https://localhost:8443/");


            productRepository.deleteById(productID);
            
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } else {
            // If not found
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

    // Blob to base64
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

    @PostMapping("/addNewProduct")
    public String addNewProduct(HttpServletRequest request, Model model,
                                @RequestParam("Title") String title,
                                @RequestParam("Description") String description,
                                @RequestParam("Category") String category,
                                @RequestParam("Price") Double price,
                                @RequestParam("Address") String Address,
                                @RequestParam("City") String City,
                                @RequestParam("Province") String Province,
                                @RequestParam("imageFile") MultipartFile imageFile,
                                @RequestParam("Cp") String Cp) throws IOException {
        //Create new product and set attributes

        Product product = new Product();
        String username = request.getUserPrincipal().getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        product.setOwner(user.getUserID());
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
            case "Hogar":
                product.setProductType(6L);
                break;
            case "Juguetes":
                product.setProductType(7L);
                break;
            default:
                break;
        }
        product.setPrice(price);
        String fullAddress = Address + ", " + City + ", " + Province + ", " + Cp;
        product.setAddress(fullAddress);
        if (!imageFile.isEmpty()) {
            product.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
            product.setImage(true);
        }
        //save con el repository
        productRepository.save(product);
        return "redirect:/profile";
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDto>> searchProducts(@RequestParam("query") String query, Model model, @RequestParam int page, @RequestParam int pageSize) {
        try {
            Pageable pageable = PageRequest.of(page, pageSize);
            Page<Product> productsPage = searchService.searchProducts(query, pageable);

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
            // Log exception
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
