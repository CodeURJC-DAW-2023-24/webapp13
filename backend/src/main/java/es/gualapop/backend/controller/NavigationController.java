package es.gualapop.backend.controller;

import javax.servlet.http.HttpServletRequest;
import es.gualapop.backend.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class NavigationController {

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request){
        if(request.isUserInRole("USER")) {
            return "profile";
        } else if (request.isUserInRole("ADMIN")) {
            return "adminPanel";
        }
        return "login";
    }

    @GetMapping("/loginerror")
    public String loginerror(Model model) {
        model.addAttribute("invalid", true);
        return "login";
    }

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("categories", productTypeRepository.findAll());
        return "index";
    }

    @GetMapping("/admin")
    public String admin() {
        return "adminPanel";
    }

    @GetMapping("/reportPanel")
    public String reportPanel() {
        return "reportPanel";
    }

    @GetMapping("/logout")
    private String logout() {
        return "index";
    }

    @GetMapping("/reportForm")
    public String reportForm() {
        return "reportForm";
    }

    @GetMapping("/signUp")
    public String signUp() {
        return "signUp";
    }


}
