package es.gualapop.backend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userID;

    private String password;

    private String fullName;
    private String username;
    private String userEmail;

    @Lob
    private Blob userImg;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Integer> roles;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Integer> reviewList;

    // Constructor, getters, and setters

    public User(){}
    public User(String username, String email, String encodedPassword, String fName, Integer... roles, Blob userImg) {
        this.username = username;
        this.password = encodedPassword;
        this.roles = List.of(roles);
        this.userEmail = email;
        this.fullName = fName;
        this.userImg = userImg;
    }

    public void setUserID(Long id) {
        this.userID = id;
    }

    public Long getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<Integer> getRoles() {
        return roles;
    }

    public void setRoles(Integer role) {
        this.roles.add(role);
    }

    public List<Integer> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Integer> reviewList) {
        this.reviewList = reviewList;
    }

    public Blob getUserImg() {
        return userImg;
    }

    public void setUserImg(Blob userImg) {
        this.userImg = userImg;
    }
}
