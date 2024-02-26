package es.gualapop.backend.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "UserTable")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userID;

    private String encodedPassword;

    private String fullName;
    private String username;
    private String userEmail;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> roles;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<Integer> reviewList;
    // Constructor, getters, and setters

    public User(){}
    public User(String username, String email, String encodedPassword, String fName, String... roles) {
        this.username = username;
        this.encodedPassword = encodedPassword;
        this.roles = List.of(roles);
        this.userEmail = email;
        this.fullName = fName;
    }

    public void setUserID(Long id) {
        this.userID = id;
    }

    public Long getUserID() {
        return userID;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String password) {
        this.encodedPassword = password;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(String role) {
        this.roles.add(role);
    }

    public List<Integer> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Integer> reviewList) {
        this.reviewList = reviewList;
    }

    public String getName() {
        return username;
    }
}
