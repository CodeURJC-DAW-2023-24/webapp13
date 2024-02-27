package es.gualapop.backend.repository;

import es.gualapop.backend.model.Product;
import es.gualapop.backend.model.ProductType;
import es.gualapop.backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNullApi;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    //Búsqueda por nombre
    List<Product> findByTitle(String prodName);

    //Búsqueda por tipo de producto
    List<Product> findByProductType(ProductType productType);

    //Búsqueda por usuario propietario del producto
    List<Product> findByOwner(User ownerId);

    //Búsqueda por rango de precios
    List<Product> findByPriceBetween(float minPrice, float maxPrice);
}

