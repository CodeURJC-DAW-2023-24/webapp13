package es.gualapop.backend.controller;

import es.gualapop.backend.model.Product;
import es.gualapop.backend.model.User;

import es.gualapop.backend.model.Review;
import es.gualapop.backend.repository.ProductRepository;
import es.gualapop.backend.repository.ReviewRepository;
import es.gualapop.backend.service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
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
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ReviewRepository reviewRepository;

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

	public double calcularMediaReviews(Long productId) {
		Product product = productRepository.findById(productId).orElse(null);
		if (product == null) {
			throw new IllegalArgumentException("El producto con ID " + productId + " no existe.");
		}

		List<Review> reviews = reviewRepository.findByProductId(productId);
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
