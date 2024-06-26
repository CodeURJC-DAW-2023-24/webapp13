package es.gualapop.backend.controller.api;

import com.fasterxml.jackson.annotation.JsonView;
import es.gualapop.backend.controller.api.RequestStructures.UserRegistrationRequest;
import es.gualapop.backend.model.Product;
import es.gualapop.backend.model.Report;
import es.gualapop.backend.model.Review;
import es.gualapop.backend.model.User;
import es.gualapop.backend.repository.ProductRepository;
import es.gualapop.backend.repository.ReportRepository;
import es.gualapop.backend.repository.ReviewRepository;
import es.gualapop.backend.repository.UserRepository;
import es.gualapop.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
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

    @Operation(summary = "Get User by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the User"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
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

    @Operation(summary = "Get User by Username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the User"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @JsonView(User.Detailed.class)
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
        Optional<User> userOptional = userService.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Download User Image by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the User Image"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @JsonView(User.Detailed.class)
    @GetMapping("/{userID}/image")
    public ResponseEntity<Object> downloadUserImage(@PathVariable long userID) throws SQLException {

        Optional<User> user = userService.findById(userID);
        if (user.isPresent() && user.get().getImageFile() != null) {

            Resource file = new InputStreamResource(user.get().getImageFile().getBinaryStream());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .contentLength(user.get().getImageFile().length())
                    .body(file);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Update User by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PatchMapping("/{userID}")
    public ResponseEntity<?> updateUser(@PathVariable Long userID,
                                        @RequestParam(required = false) String fullName,
                                        @RequestParam(required = false) String username,
                                        @RequestParam(required = false) String currentPassword,
                                        @RequestParam(required = false) String newPassword,
                                        @RequestParam(required = false) String confirmPassword,
                                        @RequestParam(required = false) MultipartFile imageFile) throws IOException{

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
            if (username != null && !username.isEmpty() && userRepository.findByUsername(username).isEmpty()) {
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

    @Operation(summary = "Delete User by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @JsonView(User.Detailed.class)
    @DeleteMapping("/{userID}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userID) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUsername = authentication.getName();
        Optional<User> userOptional = userService.findByUsername(currentUsername);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (userID.equals(user.getUserID()) || user.getRoles().contains("ADMIN")) {
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
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        
    }

    @Operation(summary = "Register User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "404", description = "User registration failed")
    })
    @JsonView(User.Detailed.class)
    @PostMapping("/")
    public ResponseEntity<Object> registerUser(@RequestPart("name") String name,
                                              @RequestPart("username") String username,
                                              @RequestPart("password") String password,
                                              @RequestPart("repeatPassword") String repeatPassword,
                                              @RequestPart("email") String email,
                                              @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        // Crear y registrar el usuario usando los datos proporcionados
        User user = new User(username, null, email, password, name, null, "USER");
    
        if (userService.checkPassword(password, repeatPassword)) {
            if (userService.registerUser(user, image)) {
                // Crear la URL de ubicación del recurso creado
                String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
                String locationUrl = baseUrl + "/users/" + user.getUserID(); // Por ejemplo, /users/{userID}
    
                // Agregar la cabecera "Location" con la URL del recurso creado
                HttpHeaders headers = new HttpHeaders();
                headers.add("Location", locationUrl);
    
                // Devolver la respuesta con el código 200 y la cabecera "Location"
                return ResponseEntity.ok().headers(headers).body("User created successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No ha sido posible registrar el usuario");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Las contraseñas no son las mismas");
        }
    }


    @Operation(summary = "Get All Products by User ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Products"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @JsonView(Product.Detailed.class)
    @GetMapping("/{id}/allProducts")
    public ResponseEntity<Object> getProducts(@PathVariable("id") long id) {
        Optional<User> user = userRepository.findByUserID(id);
        if(user.isPresent()) {
            return ResponseEntity.ok().body(productRepository.findByOwner(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get All reviews by User ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Products"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @JsonView(Review.Detailed.class)
    @GetMapping("/{userID}/reviews")
    public ResponseEntity<List<Review>> getUserReviews(@PathVariable long userID) {

        List<Review> userReviews = reviewRepository.findBySellerID(userID);

        if (!userReviews.isEmpty()) {
            return ResponseEntity.ok().body(userReviews);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}