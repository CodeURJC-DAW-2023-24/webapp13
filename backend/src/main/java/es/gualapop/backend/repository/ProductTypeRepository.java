package es.gualapop.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.gualapop.backend.model.ProductType;


public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {

    //Búsqueda por nombre
    Optional<ProductType> findByType(String type);

}