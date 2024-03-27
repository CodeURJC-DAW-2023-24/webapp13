package es.gualapop.backend.model.DTO;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getImageFileBase64() {
        return imageFileBase64;
    }

    public void setImageFileBase64(String imageFileBase64) {
        this.imageFileBase64 = imageFileBase64;
    }

    public boolean isImage() {
        return image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

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
