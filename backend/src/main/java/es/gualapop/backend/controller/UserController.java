package es.gualapop.backend.controller;

import es.gualapop.backend.model.Product;
import es.gualapop.backend.repository.ProductRepository;
import es.gualapop.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {



    @RequestMapping("/login")
    public String login() {
        return "login.html";
    }
    @RequestMapping("/loginerror")
    public String loginerror() {
        return "loginerror";
    }



}
