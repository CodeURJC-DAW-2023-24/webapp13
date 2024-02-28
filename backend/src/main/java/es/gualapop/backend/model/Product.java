package es.gualapop.backend.model;

import javax.persistence.*;
import java.sql.Blob;
import java.util.List;

@Entity(name = "ProductTable")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;

    private String title;

    private double price;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Lob
    private Blob imageFile;

    private boolean image;

    private Long owner;

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
        return owner;
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

    public void setId(long id) {
        this.id = id;
    }

    public Blob getImageFile() {
        return imageFile;
    }

    public void setImageFile(Blob image) {
        this.imageFile = image;
    }

    public boolean getImage(){
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

    @Override
    public String toString() {
        return "Product [id=" + id + ", title=" + title + ", description=" + description + "]";
    }
}
