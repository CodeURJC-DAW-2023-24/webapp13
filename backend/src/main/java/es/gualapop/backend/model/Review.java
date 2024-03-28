package es.gualapop.backend.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Review {
    public interface Basic{}
    public interface Detailed extends Review.Basic{}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Basic.class)
    private Long reviewID;
    @JsonView(Basic.class)
    private float rating;
    @JsonView(Basic.class)
    private Long sellerID;

    public Review(){}
    public Review(float rating, Long sellerID) {
        this.rating = rating;
        this.sellerID = sellerID;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Long getSellerID() {
        return sellerID;
    }

    public void setSellerID(Long sellerID) {
        this.sellerID = sellerID;
    }

    public void setReviewID(Long id) {
        this.reviewID = id;
    }

    public Long getReviewID() {
        return reviewID;
    }
}
