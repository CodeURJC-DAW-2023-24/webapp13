package es.gualapop.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long owner;
    private String title;
    private String description;
    private Long userReported;
    private String creationDate;


    public Report() {
    }

    public Report(Long owner, String title, String description, Long userReported,String creationDate) {
        this.owner = owner;
        this.title = title;
        this.description = description;
        this.userReported = userReported;
        this.creationDate = creationDate;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUserReported() {
        return userReported;
    }

    public void setUserReported(Long userReported) {
        this.userReported = userReported;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
