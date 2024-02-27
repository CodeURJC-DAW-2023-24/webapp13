package es.gualapop.backend.controller;

import es.gualapop.backend.model.Product;
import es.gualapop.backend.repository.ProductRepository;
import es.gualapop.backend.service.LoaderService;
import es.gualapop.backend.service.ProductService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private LoaderService loaderService;

    @RequestMapping("/login")
    public String login() throws IOException {
        loaderService.Load();
        return "login.html";
    }
    @RequestMapping("/loginerror")
    public String loginerror() {
        return "loginerror.html";
    }

    @RequestMapping("/index")
    public String index() {
        return "index.html";
    }

    @RequestMapping("/admin")
    public String admin() {
        return "adminPanel.html";
    }

    @RequestMapping("/newProduct")
    public String newProduct() {
        return "newProduct.html";
    }

    @RequestMapping("/reportPanel")
    public String reportPanel() {
        return "reportPanel.html";
    }

    @RequestMapping("/profile")
    public String profile() {
        return "profile.html";
    }

    @RequestMapping("/productoIndividual")
    public String productoIndividual() {
        return "productoIndividual.html";
    }

    @RequestMapping("/profileConsult")
    public String profileConsult() {
        return "profileConsult.html";
    }

    @RequestMapping("/reportForm")
    public String reportForm() {
        return "reportForm.html";
    }

    @RequestMapping("/signUp")
    public String signUp() {
        return "signUp.html";
    }

}
