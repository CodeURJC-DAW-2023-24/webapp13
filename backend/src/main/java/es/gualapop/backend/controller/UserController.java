package es.gualapop.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
