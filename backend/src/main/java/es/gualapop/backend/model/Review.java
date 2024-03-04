package es.gualapop.backend.model;

import javax.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reviewID;

    private float rating;
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
