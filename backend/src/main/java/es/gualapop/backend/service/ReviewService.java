package es.gualapop.backend.service;

import es.gualapop.backend.model.Review;
import es.gualapop.backend.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void save(Review review) {
        reviewRepository.save(review);
    }
    public List<Review> findReviewsByRatingBetween(float min, float max) {
        return reviewRepository.findReviewsByRatingBetween(min, max);
    }
    public void delete(long id) {
        reviewRepository.deleteById(id);
    }

    public List<Review> getReviewsByWriter(Long writerID) {
        return reviewRepository.findReviewsByWriterID(writerID);
    }


}
