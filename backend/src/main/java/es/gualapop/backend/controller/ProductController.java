package es.gualapop.backend.controller;

import es.gualapop.backend.model.User;
import es.gualapop.backend.model.Product;
import es.gualapop.backend.repository.ProductRepository;
import es.gualapop.backend.repository.UserRepository;
import es.gualapop.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String getProducts(Model model) {
        if(productRepository.findAll().isEmpty()) {
            model.addAttribute("empty", true);
        } else {
            model.addAttribute("products", productRepository.findAll());
        }
        return "index";
    }

    @GetMapping("/product/{id}")
    public String getProduct(Model model, @PathVariable long id) {
        Product p = productService.getProductById(id);
        User u = userRepository.getOne(p.getOwner().getUserID());
        model.addAttribute("user", u);
        model.addAttribute("product", p);

        //Añadir la parte de seguridad para el boton reportar

        return "productoIndividual";
    }
}
