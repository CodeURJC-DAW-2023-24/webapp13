package es.gualapop.backend.controller;

import es.gualapop.backend.model.Product;
import es.gualapop.backend.model.Report;
import es.gualapop.backend.model.User;

import es.gualapop.backend.model.Review;
import es.gualapop.backend.repository.ProductRepository;
import es.gualapop.backend.repository.ProductTypeRepository;
import es.gualapop.backend.repository.ReportRepository;
import es.gualapop.backend.repository.ReviewRepository;
import es.gualapop.backend.repository.UserRepository;
import es.gualapop.backend.service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	@Autowired
	private ReportRepository reportRepository;

    @PostMapping("/registerUser")
	public String registerUser(Model model, String name, String username, String password, String repeatPassword, String email,
                                @RequestParam(required = false) MultipartFile image) throws IOException, SQLException {

		User user = new User(username, null, email, password, name, null,"USER");
		if(userService.checkPassword(password, repeatPassword)){
			model.addAttribute("error",false);
			if (!userService.registerUser(user, image)) {
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

	@GetMapping("/profile")
	    public String profile(Model model, HttpServletRequest request) {

        String name = request.getUserPrincipal().getName();

        User user = userRepository.findUserByUsername(name).orElseThrow();
        model.addAttribute("rating", calculateReviewsMean(user.getUserID()));

        if(request.isUserInRole("USER") && !request.isUserInRole("ADMIN")) {

            model.addAttribute("user", user);
            return "profile";
        }

        return "redirect:/getReports";
    }

	@GetMapping("/getMyProducts")
    public String getMyProducts(Model model, HttpServletRequest request, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int pageSize) {
        try {
			Pageable pageable = PageRequest.of(page, pageSize);

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentUsername = authentication.getName();

			// Set earnings to seller
			Optional<User> thisUser = userRepository.findByUsername(currentUsername);
			if (thisUser.isPresent()){
				User user = thisUser.get();
				Page<Product> productsPage = productRepository.findByOwner(user.getUserID(), pageable);
				if ((double)page >= (double) productsPage.getTotalElements() / (double)pageSize){
					double sub = Math.ceil(productsPage.getTotalElements() / pageSize) + 1;
					int totalPages = (int) sub;
					int pages = page - totalPages;
					while (pages > (totalPages - 1)){
						pages -= totalPages;
					}
					pageable = PageRequest.of(pages, pageSize);
					productsPage = productRepository.findAll(pageable);
				}

				model.addAttribute("products", productsPage.getContent());
			}

			return "myProductIndex";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
    }

	@PostMapping("/updateUser/{userID}")
    public String updateUser(@PathVariable Long userID, Model model,
                             @RequestParam String fullName,
                             @RequestParam String username,
                             @RequestParam String currentPassword,
                             @RequestParam String newPassword,
                             @RequestParam String confirmPassword,
                             @RequestParam(required = false) MultipartFile imageFile) throws IOException, SQLException {

        Optional<User> optionalUser = userRepository.findById(userID);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			// Verify password and new password
			if (!newPassword.isEmpty() && newPassword.equals(confirmPassword) && passwordEncoder.matches(currentPassword, user.getEncodedPassword())) {
				user.setEncodedPassword(userService.encodePassword(newPassword));
			}
			// Update user
			if (!fullName.isEmpty()) {
				user.setFullName(fullName);
			}
			// Verify and update image
			if (imageFile.getOriginalFilename() != "" && !imageFile.isEmpty()) {
				user.setUserImg(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
			}

			if (!username.isEmpty() && !userRepository.findByUsername(username).isPresent()) {
				user.setUsername(username);
				userRepository.save(user);
				return "redirect:/logout";
			}

			// Save updated user
			userRepository.save(user);

			// Redirect to profile
			return "redirect:/profile";
		} else {
			return "error";
		}
    }

	 @GetMapping("/deleteAccount/{id}")
    public String deleteUser(Model model,@PathVariable("id") Long userID) {

        List<Product> userProducts = productRepository.findByOwner(userID);
        for (Product eachProduct : userProducts) {
            productRepository.deleteById(eachProduct.getId());
        }
        List<Report> userReported = reportRepository.findByUserReported(userID);
        for (Report eachReport : userReported) {
            reportRepository.deleteById(eachReport.getId());
        }
        List<Report> userReports = reportRepository.findByOwner(userID);
        for (Report eachReport : userReports) {
            reportRepository.deleteById(eachReport.getId());
        }
		List<Review> userReview = reviewRepository.findBySellerID(userID);
        for (Review eachReview : userReview) {
            reviewRepository.deleteById(eachReview.getReviewID());
        }

        userRepository.deleteById(userID);

        return "redirect:/logout";
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
    public String userProfile(Model model, @PathVariable("userID") long userID, HttpServletRequest request) {
        Optional<User> optionalUser = userRepository.findById(userID);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            model.addAttribute("user", user);
            model.addAttribute("categories", productTypeRepository.findAll());
            model.addAttribute("rating", calculateReviewsMean(userID)); // Modify if there's a rating system implemented
            model.addAttribute("products", productRepository.findByOwner(user.getUserID()));
			model.addAttribute("logged", request.isUserInRole("USER"));
            return "profileConsult";
        } else {
            model.addAttribute("error", true);
            model.addAttribute("error.message", "User doesn't exist");
            return "error";
        }
    }

	public double calculateReviewsMean(Long userID) {
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
