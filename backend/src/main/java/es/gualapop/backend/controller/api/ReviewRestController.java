package es.gualapop.backend.controller.api;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonView;

import es.gualapop.backend.model.Product;
import es.gualapop.backend.model.Report;
import es.gualapop.backend.model.Review;
import es.gualapop.backend.model.User;
import es.gualapop.backend.repository.ReviewRepository;
import es.gualapop.backend.repository.UserRepository;
import es.gualapop.backend.service.ReviewService;

@RestController
@CrossOrigin
@RequestMapping("api/reviews")
public class ReviewRestController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Get all reviews")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found reviews"),
            @ApiResponse(responseCode = "404", description = "No reviews found")
    })
    @JsonView(Review.Detailed.class)
    @GetMapping("/")
    public ResponseEntity<List<Review>> getAllProducts(){
        List<Review> reviews = reviewRepository.findAll();
        if (reviews.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(reviews);
        }
    }

    @Operation(summary = "Get review by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the review"),
            @ApiResponse(responseCode = "404", description = "Review not found")
    })
    @JsonView(Review.Detailed.class)
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewByID(@PathVariable("id") long idReview) {
        Optional<Review> reviewOptional = reviewRepository.findById(idReview);

        if (reviewOptional.isPresent()) {
            Review review = reviewOptional.get();
            return ResponseEntity.ok().body(review);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Create a new review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Review created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Seller not found")
    })
    @JsonView(Review.Detailed.class)
    @PostMapping("/new")
    public ResponseEntity<String> postReview(HttpServletRequest request,
                                               @RequestParam("rating") float rating,
                                               @RequestParam("sellerID") Long sellerID) {
        Review review = new Review();
        Optional<User> user = userRepository.findById(sellerID);
        if(!user.isPresent()){
            return ResponseEntity.badRequest().body("Seller not found");
        }
        if(rating<0 || rating>5){
            return ResponseEntity.badRequest().body("Review's rating must be between 0 and 5");
        }
        
        review.setRating(rating);
        review.setSellerID(sellerID);

        reviewRepository.save(review);

        return ResponseEntity.status(HttpStatus.CREATED).body("Review added successfully");
    }

    @Operation(summary = "Delete a review by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Review not found")
    })
    @JsonView(Review.Detailed.class)
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") long idReview){
        Optional<Review> review = reviewRepository.findById(idReview);
        if (review.isPresent()){
            reviewRepository.deleteById(review.get().getReviewID());
            return ResponseEntity.ok("Review deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(summary = "Update a review by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Review not found")
    })
    @JsonView(Review.Detailed.class)
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateReview(@PathVariable long id,
                                          @RequestParam(required = false) Float rating,
                                          @RequestParam(required = false) Long sellerID) {
        Optional<Review> optionalReview = reviewRepository.findById(id);

        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();

            if (rating != null) {
                if (rating >= 0 && rating <= 5) {
                    review.setRating(rating);
                } else {
                    return ResponseEntity.badRequest().body("Rating must be between 0 and 5");
                }
            }

            if (sellerID != null) {
                // Verificar si el usuario existe
                Optional<User> optionalUser = userRepository.findById(sellerID);
                if (!optionalUser.isPresent()) {
                    return ResponseEntity.badRequest().body("User with ID " + sellerID + " not found");
                }
                review.setSellerID(sellerID);
            }

            reviewRepository.save(review);

            return ResponseEntity.ok().body(review);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
