package es.gualapop.backend.controller;

import es.gualapop.backend.model.Product;
import es.gualapop.backend.model.User;

import es.gualapop.backend.model.Review;
import es.gualapop.backend.repository.ProductRepository;
import es.gualapop.backend.repository.ProductTypeRepository;
import es.gualapop.backend.repository.ReviewRepository;
import es.gualapop.backend.repository.UserRepository;
import es.gualapop.backend.service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class UserController {

    @Autowired
	private UserService userService;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductTypeRepository productTypeRepository;
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private UserRepository userRepository;

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
	@PostMapping("/updateUser/{userID}")
    public String updateUser(@PathVariable Long userID, Model model,
                             @RequestParam String fullName,
                             @RequestParam String username,
                             @RequestParam String currentPassword,
                             @RequestParam String newPassword,
                             @RequestParam String confirmPassword,
                             @RequestParam(required = false) MultipartFile image) throws IOException, SQLException {

        Optional<User> optionalUser = userRepository.findById(userID);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

			// Verificar y actualizar la contraseña si se proporciona una nueva
			if (!newPassword.isEmpty() && newPassword.equals(confirmPassword)) {
				user.setEncodedPassword(userService.encodePassword(newPassword));
			}
			// Actualizar otros campos
			if (!fullName.isEmpty()) {
				user.setFullName(fullName);
			}
			if (!username.isEmpty() && !userRepository.findByUsername(username).isPresent()) {
				user.setUsername(username);
			}

			// Verificar y actualizar la imagen si se proporciona una nueva
			if (image != null && !image.isEmpty()) {
				user.setUserImg(BlobProxy.generateProxy(image.getInputStream(), image.getSize()));
			}

			// Guardar el usuario actualizado
			userRepository.save(user);

			// Redirigir a la página de perfil actualizada
			return "redirect:/profile";
		} else {
			// Contraseña incorrecta, manejar el error
			//model.addAttribute("error", true);
			//model.addAttribute("error.message", "Contraseña incorrecta");
			return "error";
		}
    }

	@GetMapping("/user/{userID}/image")
	public ResponseEntity<Object> downloadUserImage(@PathVariable long userID) throws SQLException {

		Optional<User> user = userService.findById(userID);
		if (user.isPresent() && user.get().getImageFile() != null) {

			Resource file = new InputStreamResource(user.get().getImageFile().getBinaryStream());

			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpg")
					.contentLength(user.get().getImageFile().length()).body(file);

		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/user/{userID}")
    public String userProfile(Model model, @PathVariable("userID") long userID) {
        Optional<User> optionalUser = userRepository.findById(userID);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            model.addAttribute("user", user);
            model.addAttribute("categories", productTypeRepository.findAll());
            model.addAttribute("rating", calcularMediaReviews(userID)); // Modify if there's a rating system implemented
            model.addAttribute("products", productRepository.findByOwner(user.getUserID()));
            return "profileConsult";
        } else {
            model.addAttribute("error", true);
            model.addAttribute("error.message", "No existe este usuario");
            return "error";

        }
    }

	public double calcularMediaReviews(Long userID) {
		List<Review> reviews = reviewRepository.findBySellerID(userID);
		for (Review r : reviews){
			System.out.println(r.getRating());
		}
		if (reviews.isEmpty()) {
			return 0.0;
		}

		double sum = 0;
		for (Review review : reviews) {
			sum += review.getRating();
		}

		return sum / reviews.size();
	}
}
