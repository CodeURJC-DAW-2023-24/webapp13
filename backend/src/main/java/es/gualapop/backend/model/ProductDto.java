package es.gualapop.backend.model;

public class ProductDto {

    private Long id;
    private String title;
    private String address;
    private double price;
    private String description;
    private String imageFileBase64;
    private boolean image;
    private Long owner;
    private Long productType;

    // Getter y Setter para id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter y Setter para title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter y Setter para address
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Getter y Setter para price
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Getter y Setter para description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter y Setter para imageFileBase64
    public String getImageFileBase64() {
        return imageFileBase64;
    }

    public void setImageFileBase64(String imageFileBase64) {
        this.imageFileBase64 = imageFileBase64;
    }

    // Getter y Setter para image
    public boolean isImage() {
        return image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }

    // Getter y Setter para owner
    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    // Getter y Setter para productType
    public Long getProductType() {
        return productType;
    }

    public void setProductType(Long productType) {
        this.productType = productType;
    }

    public ProductDto(Long id, String title, String address, double price, String description,
    String imageFileBase64, boolean image, Long owner, Long productType) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.price = price;
        this.description = description;
        this.imageFileBase64 = imageFileBase64;
        this.image = image;
        this.owner = owner;
        this.productType = productType;
        }
}
