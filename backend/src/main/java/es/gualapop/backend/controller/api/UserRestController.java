package es.gualapop.backend.controller.api;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.gualapop.backend.model.Product;
import es.gualapop.backend.model.Report;
import es.gualapop.backend.model.Review;
import es.gualapop.backend.model.User;
import es.gualapop.backend.repository.ProductRepository;
import es.gualapop.backend.repository.ReportRepository;
import es.gualapop.backend.repository.ReviewRepository;
import es.gualapop.backend.repository.UserRepository;
import es.gualapop.backend.service.UserService;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/user")
public class UserRestController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper; // ObjectMapper para la conversión JSON

    @JsonView(User.Detailed.class)
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserByID(@PathVariable("id") long idUser) {
        Optional<User> userOptional = userService.findById(idUser);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @JsonView(User.Detailed.class)
    @GetMapping("/{userID}/image")
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

    @PatchMapping("/{userID}")
    public ResponseEntity<?> updateUser(@PathVariable Long userID,
                                        @RequestParam(required = false) String fullName,
                                        @RequestParam(required = false) String username,
                                        @RequestParam(required = false) String currentPassword,
                                        @RequestParam(required = false) String newPassword,
                                        @RequestParam(required = false) String confirmPassword,
                                        @RequestParam(required = false) MultipartFile imageFile) throws IOException, SQLException {

        Optional<User> optionalUser = userRepository.findById(userID);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            // Verificar contraseña y nueva contraseña
            if (currentPassword != null && newPassword != null && confirmPassword != null &&
                    !currentPassword.isEmpty() && !newPassword.isEmpty() && !confirmPassword.isEmpty() &&
                    newPassword.equals(confirmPassword) && passwordEncoder.matches(currentPassword, user.getEncodedPassword())) {
                user.setEncodedPassword(userService.encodePassword(newPassword));
            }

            // Actualizar usuario
            if (fullName != null && !fullName.isEmpty()) {
                user.setFullName(fullName);
            }

            // Verificar y actualizar imagen
            if (imageFile != null && !imageFile.isEmpty()) {
                user.setUserImg(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
            }

            // Verificar y actualizar nombre de usuario
            if (username != null && !username.isEmpty() && !userRepository.findByUsername(username).isPresent()) {
                user.setUsername(username);
                userRepository.save(user);
                return ResponseEntity.ok().body("User updated successfully");
            }

            // Guardar usuario actualizado
            userRepository.save(user);

            // Redirigir al perfil
            return ResponseEntity.ok().body("User updated successfully");
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }
    @JsonView(User.Detailed.class)
    @DeleteMapping("/{userID}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userID) {

        // Eliminar productos del usuario
        List<Product> userProducts = productRepository.findByOwner(userID);
        for (Product eachProduct : userProducts) {
            productRepository.deleteById(eachProduct.getId());
        }

        // Eliminar reportes relacionados con el usuario
        List<Report> userReported = reportRepository.findByUserReported(userID);
        for (Report eachReport : userReported) {
            reportRepository.deleteById(eachReport.getId());
        }

        // Eliminar reportes creados por el usuario
        List<Report> userReports = reportRepository.findByOwner(userID);
        for (Report eachReport : userReports) {
            reportRepository.deleteById(eachReport.getId());
        }

        // Eliminar revisiones relacionadas con el usuario
        List<Review> userReview = reviewRepository.findBySellerID(userID);
        for (Review eachReview : userReview) {
            reviewRepository.deleteById(eachReview.getReviewID());
        }

        // Eliminar usuario
        userRepository.deleteById(userID);

        return ResponseEntity.ok().body("User deleted successfully");
    }

}