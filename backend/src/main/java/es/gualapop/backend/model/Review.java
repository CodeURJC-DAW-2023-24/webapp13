package es.gualapop.backend.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "ReviewTable")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reviewID;

    private float rating;
    private String description;
    private Long writerID;
    private Long sellerID;

    public Review(){}
    public Review(float rating, String description, Long writerID, Long sellerID){
        this.rating = rating;
        this.description = description;
        this.writerID = writerID;
        this.sellerID = sellerID;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getWriterID() {
        return writerID;
    }

    public void setWriterID(Long writerID) {
        this.writerID = writerID;
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
