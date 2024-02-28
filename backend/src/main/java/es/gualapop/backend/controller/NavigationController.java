package es.gualapop.backend.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.gualapop.backend.service.LoaderService;

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
    @GetMapping("/error")
    public String error404(){
        return "error";
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

    @RequestMapping("/profiles")
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
