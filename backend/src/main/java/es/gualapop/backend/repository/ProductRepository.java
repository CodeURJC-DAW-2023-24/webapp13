package es.gualapop.backend.repository;

import es.gualapop.backend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByTitle(String prodName);

    List<Product> findByOwner(Long owner);
    Page<Product> findByOwner(Long owner, Pageable page);

    List<Product> findProductsByProductType(Long type);
    Page<Product> findProductsByProductType(Long type, Pageable pageable);


    List<Product> findByPriceBetween(double price, double price2);

    Page<Product> findByTitleContainingIgnoreCaseOrTitleEqualsIgnoreCase(String query, String query1, Pageable pageable);

    void deleteById(Long id);

    Page<Product> findProductsByProductTypeAndIdNot(Long productTypeId, Long product, Pageable pageable);
}

