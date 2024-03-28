package es.gualapop.backend.controller.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.gualapop.backend.model.Review;
import es.gualapop.backend.repository.ReviewRepository;

@RestController
@CrossOrigin
@RequestMapping("api/reviews")
public class ReviewRestController {

    @Autowired
    private ReviewRepository reviewRepository;

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
