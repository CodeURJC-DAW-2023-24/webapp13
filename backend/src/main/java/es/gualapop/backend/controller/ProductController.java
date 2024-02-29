package es.gualapop.backend.controller;

import es.gualapop.backend.model.User;
import es.gualapop.backend.model.Product;
import es.gualapop.backend.repository.ProductRepository;
import es.gualapop.backend.repository.UserRepository;
import es.gualapop.backend.service.ProductService;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String getProducts(Model model, HttpServletRequest request) {
        if(productRepository.findAll().isEmpty()) {
            model.addAttribute("products", false);
        } else {
            model.addAttribute("products", productRepository.findAll());
        }

        return "index";
    }

    @GetMapping("/product/{id}")
    public String getProduct(HttpServletRequest request, Model model, @PathVariable long id) {
        Product p = productService.getProductById(id);
        User u = userRepository.getOne(p.getOwner());
        model.addAttribute("user", u);
        model.addAttribute("product", p);

        // Verificar si el usuario está autenticado
        if (request.getUserPrincipal() != null) {
            String name = request.getUserPrincipal().getName();
            User user = userRepository.findUserByUsername(name).orElseThrow();
            model.addAttribute("admin", request.isUserInRole("ADMIN"));
        } else {
            // Manejar el caso cuando el usuario no está autenticado
            model.addAttribute("admin", false);
        }

        return "productoIndividual";
    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<Object> downloadImage(@PathVariable long id) throws SQLException {

        Optional<Product> product = productService.findById(id);
        if (product.isPresent() && product.get().getImageFile() != null) {

            Resource file = new InputStreamResource(product.get().getImageFile().getBinaryStream());

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpg")
                    .contentLength(product.get().getImageFile().length()).body(file);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public String searchProducts(Model model) {
        return "/";
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
