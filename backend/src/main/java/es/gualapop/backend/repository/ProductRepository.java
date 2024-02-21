package es.gualapop.backend.repository;

import es.gualapop.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    //Búsqueda por nombre
    List<Product> findByProdName(String prodName);

    //Búsqueda por tipo de producto
    List<Product> findByProdTypeId(long prodTypeId);

    //Búsqueda por usuario propietario del producto
    List<Product> findByOwnerId(long ownerId);

    //Búsqueda por rango de precios
    List<Product> findByPriceBetween(float minPrice, float maxPrice);

}

