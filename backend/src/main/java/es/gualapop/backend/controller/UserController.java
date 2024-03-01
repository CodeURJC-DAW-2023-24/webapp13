package es.gualapop.backend.controller;

import es.gualapop.backend.model.User;

import es.gualapop.backend.service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class UserController {

    @Autowired
	private UserService userService;
    
    @PostMapping("/registerUser")
	public String registerUser(Model model, String name, String username, String password, String repeatPassword, String email,
                                @RequestParam(required = false) MultipartFile image) throws IOException, SQLException {

		User user = new User(username, null, email, password, name, null,"USER");
		if(userService.checkPassword(password, repeatPassword)){
			model.addAttribute("error",false);
			if (!userService.registerUsers(user, image)) {
				model.addAttribute("hasBeenRegistered", true);
				return "signUp";
			} else {
				model.addAttribute("hasBeenRegistered", false);
				return "login";
			}
		}
        model.addAttribute("error",true);
		return "signUp";
    }


}
