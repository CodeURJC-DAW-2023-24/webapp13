package es.gualapop.backend.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonView;

import java.sql.Blob;

@Entity
public class Product {

    public interface Basic{}
	public interface Detailed extends Product.Basic{}

    @JsonView(Basic.class)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;

    @JsonView(Basic.class)
    private String title;

    @JsonView(Basic.class)
    private String address;

    @JsonView(Basic.class)
    private double price;

    @JsonView(Basic.class)
    @Column(columnDefinition = "TEXT")
    private String description;

    @JsonView(Basic.class)
    @Lob
    private Blob imageFile;

    @JsonView(Basic.class)
    private boolean image;

    @JsonView(Basic.class)
    private Long owner;

    @JsonView(Basic.class)
    private Long productType;

    public Product(Long owner) {
        this.owner = owner;
    }

    public Product(String name, String description, Long owner, double price, Long productType) {
        super();
        this.title = name;
        this.description = description;
        this.owner = owner;
        this.price = price;
        this.productType = productType;
    }

    public Product() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getOwner() {
        return this.owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Blob getImageFile() {
        return imageFile;
    }

    public void setImageFile(Blob image) {
        this.imageFile = image;
    }

    public boolean isImage(){
        return this.image;
    }

    public void setImage(boolean image){
        this.image = image;
    }

    public Long getProductType() {
        return productType;
    }

    public void setProductType(Long productType) {
        this.productType = productType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", title=" + title + ", description=" + description + "]";
    }
}
