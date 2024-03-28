package es.gualapop.backend.controller.api;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @JsonView(Review.Detailed.class)
    @GetMapping("/seller/{id}")
    public ResponseEntity<List<Review>> getReviewsBySellerID(@PathVariable("id") long sellerID) {
        List<Review> reviews = reviewRepository.findBySellerID(sellerID);

        if (reviews.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(reviews);
        }
    }

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
}
