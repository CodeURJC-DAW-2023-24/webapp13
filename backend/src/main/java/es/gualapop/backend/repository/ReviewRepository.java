package es.gualapop.backend.repository;

import java.util.List;

import es.gualapop.backend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    public List<Review> findBySellerID(Long sellerID);

    public Long deleteByReviewID(Long iduser);

    public List<Review> findReviewsByRatingBetween(float min, float max);

    List<Review> findAll();
}
