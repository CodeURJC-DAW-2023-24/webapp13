package es.gualapop.backend.repository;

import es.gualapop.backend.model.Product;
import es.gualapop.backend.model.ProductType;
import es.gualapop.backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNullApi;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    //Búsqueda por nombre
    List<Product> findByTitle(String prodName);

    //Búsqueda por usuario propietario del producto
    List<Product> findByOwner(Long owner);

    List<Product> findProductsByProductType(Long type);

    //Búsqueda por rango de precios
    List<Product> findByPriceBetween(double price, double price2);

    //Busqueda por titulo ignora

    Optional<Product> findById(long id);

    List<Product> findByTitleContainingIgnoreCaseOrTitleEqualsIgnoreCase(String query, String query1);
}

