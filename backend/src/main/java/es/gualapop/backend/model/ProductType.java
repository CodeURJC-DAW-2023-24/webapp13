package es.gualapop.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ProductType {

    @Id
    private Long productTypeID;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setProductTypeID(Long id) {
        this.productTypeID = id;
    }

    public Long getProductTypeID() {
        return productTypeID;
    }
}
