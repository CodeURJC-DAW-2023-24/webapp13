package es.gualapop.backend.controller;

import es.gualapop.backend.model.User;
import es.gualapop.backend.model.Product;
import es.gualapop.backend.repository.ProductRepository;
import es.gualapop.backend.repository.ProductTypeRepository;
import es.gualapop.backend.repository.UserRepository;
import es.gualapop.backend.service.ProductService;
import es.gualapop.backend.service.SearchService;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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
import java.sql.SQLException;
import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;


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
    @GetMapping("/")
    public String getProducts(Model model, HttpServletRequest request) {
        model.addAttribute("categories", productTypeRepository.findAll());
        if(productRepository.findAll().isEmpty()) {
            model.addAttribute("products", false);
        } else {
            model.addAttribute("products", productRepository.findAll());
        }

        return "index";
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

    @PostMapping("/purchase/{productID}")
    public String purchase(@PathVariable("productID") Long productID) {
        Optional<Product> products = productRepository.findById(productID);
        if (products.isPresent()){
            Product product = products.get();
            Double price = product.getPrice();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = authentication.getName();
            Optional<User> vendedor = userRepository.findById(product.getOwner());
            if (vendedor.isPresent()){
                User user = vendedor.get();
                user.setIngresos(user.getIngresos()+price);
                userRepository.save(user);
            }
            Optional<User> comprador = userRepository.findByUsername(currentUsername);
            if (comprador.isPresent()){
                User usuario = comprador.get();
                usuario.setGastos(usuario.getGastos()+price);
                userRepository.save(usuario);
            }
            productRepository.deleteById(productID);
        } else {
            // Manejar el caso donde el producto no se encuentra
            return "error";
        }

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

}
