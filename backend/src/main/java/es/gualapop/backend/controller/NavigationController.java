package es.gualapop.backend.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.gualapop.backend.service.LoaderService;

@Controller
public class NavigationController {

    @Autowired
    private LoaderService loaderService;

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
    public String login(){
        return "login";
    }
    /*@GetMapping("/error")
    public String error404(){
        return "error";
    }*/
    @GetMapping("/loginerror")
    public String loginerror() {
        return "loginerror";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/admin")
    public String admin() {
        return "adminPanel";
    }

    @GetMapping("/newProduct")
    public String newProduct() {
        return "newProduct";
    }

    @GetMapping("/reportPanel")
    public String reportPanel() {
        return "reportPanel";
    }

    @GetMapping("/profiles")
    public String profile() {
        return "profile";
    }

    @GetMapping("/productoIndividual")
    public String productoIndividual() {
        return "productoIndividual";
    }

    @GetMapping("/profileConsult")
    public String profileConsult() {
        return "profileConsult";
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