package es.gualapop.backend.controller;

import es.gualapop.backend.model.Product;
import es.gualapop.backend.model.User;
import es.gualapop.backend.repository.ProductRepository;
import es.gualapop.backend.repository.UserRepository;
import es.gualapop.backend.service.LoaderService;
import es.gualapop.backend.service.ProductService;
import es.gualapop.backend.service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class UserController {

    @Autowired
	private UserService userService;
    
    @PostMapping("/registerUser")
	private void registerUser(String name, String username, String password, String email,
                                HttpServletResponse response, HttpServletRequest sesion,
                                @RequestParam(required = false) MultipartFile image) throws IOException, SQLException {

		List<String> roles = List.of("USER");
		User user = new User(username, null, email, password, name, roles, null); 

		System.out.println("EL PEPE ENTRA EN EL SISTEMA");
		System.out.println(password);
        userService.registerUsers(user, image);
        response.sendRedirect("/login");
    }

    @GetMapping("/imageprofile")
    private ResponseEntity<Object> downloadImage(HttpServletRequest request) throws SQLException, IOException{
    	User user = userService.getUser(request.getUserPrincipal().getName());
    	Resource file = new InputStreamResource(user.getUserImg().getBinaryStream());
    	return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
				.contentLength(user.getUserImg().length())
				.body(file);
    }

	@PostMapping("/logincheck")
	private void loginCheck(String email, String password, HttpServletResponse response) throws IOException {
		boolean found = false;
		System.out.println(email);
		List<User> users = userService.findByUserEmail(email);
		if (users.size() == 0){
			response.sendRedirect("/loginerror");
		}
		for (User user : users) {
			if (password.equals(user.getEncodedPassword())) {
				found = true;
				break; // No necesitas seguir iterando
			}
		}
		
		if (found) {
			response.sendRedirect("/");
		} else {
			response.sendRedirect("/loginerror");
		}
	}
	

}
