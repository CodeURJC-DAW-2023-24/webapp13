package es.gualapop.backend.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.gualapop.backend.model.User;
import es.gualapop.backend.repository.ProductRepository;
import es.gualapop.backend.repository.ProductTypeRepository;
import es.gualapop.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.gualapop.backend.service.LoaderService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NavigationController {

    @Autowired
    private LoaderService loaderService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;
    /*    @GetMapping("/")
	private void getInitialPage(Model model,HttpServletRequest request, HttpServletResponse response) throws IOException {
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		model.addAttribute("token", token.getToken());
        //cargar los datos en la BBDD
        loaderService.Load();
		response.sendRedirect("/index");
	}*/


    /*@GetMapping("/login")
	private String getSignIn(Model model,HttpServletRequest request, HttpSession sesion, HttpServletResponse response) throws IOException {
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		model.addAttribute("token", token.getToken());
		model.addAttribute("login", (request.getSession(false) != null));
		if(request.getUserPrincipal() != null){
			response.sendRedirect("/index");
		}
		return "login";
	}*/

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
