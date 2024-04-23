package es.gualapop.backend.controller.api;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonView;


import es.gualapop.backend.model.Review;
import es.gualapop.backend.model.User;
import es.gualapop.backend.repository.ReviewRepository;
import es.gualapop.backend.repository.UserRepository;

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

    @Operation(summary = "Get all reviews")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found reviews"),
            @ApiResponse(responseCode = "404", description = "No reviews found")
    })
    @JsonView(Review.Detailed.class)
    @PostMapping("/")
    public ResponseEntity<List<Review>> createReview(){
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
}
