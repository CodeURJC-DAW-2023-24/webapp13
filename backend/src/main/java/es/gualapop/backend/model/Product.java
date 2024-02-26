package es.gualapop.backend.model;

import javax.persistence.*;
import java.sql.Blob;

@Entity(name = "ProductTable")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;

    private String title;

    private float price;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Lob
    private Blob imageFile;

    private boolean image;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToOne
    @JoinColumn(name = "product_type_id")
    private ProductType productType;

    public Product(User owner) {
        this.owner = owner;
    }

    public Product(String nombre, String description, User owner) {
        super();
        this.title = nombre;
        this.description = description;
        this.owner = owner;
    }

    public Product() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
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

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", title=" + title + ", description=" + description + "]";
    }
}
