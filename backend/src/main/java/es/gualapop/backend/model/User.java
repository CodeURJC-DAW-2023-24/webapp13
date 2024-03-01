package es.gualapop.backend.model;

import javax.persistence.*;

import java.sql.Blob;
import java.util.ArrayList;
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
    private Double ingresos;
    private Double gastos;
    @Lob
    private Blob userImg;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<Integer> reviewList;
    // Constructor, getters, and setters

    public User(){}
    public User(String username, Blob userImg, String email, String encodedPassword, String fName, List<Integer> reviews, String... roles) {
        this.username = username;
        this.userImg = userImg;
        this.encodedPassword = encodedPassword;
        this.roles = List.of(roles);
        this.userEmail = email;
        this.fullName = fName;
        this.reviewList = reviews;
        this.ingresos = (double) 0;
        this.gastos = (double) 0;
    }

    public void setUserID(Long id) {
        this.userID = id;
    }

    public Long getUserID() {
        return userID;
    }

    public void setIngresos(Double ingresos) {
        this.ingresos = ingresos;
    }

    public Double getIngresos() {
        return ingresos;
    }

    public void setGastos(Double gastos) {
        this.gastos = gastos;
    }

    public Double getGastos() {
        return gastos;
    }

    public void setUserImg(Blob userImg) {
        this.userImg = userImg;
    }

    public Blob getUserImg() {
        return userImg;
    }
    
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
