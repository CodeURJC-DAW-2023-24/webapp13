package es.gualapop.backend.repository;

import java.util.List;
import java.util.Optional;

import es.gualapop.backend.model.ProductType;

public interface ProductTypeRepository {

    //Búsqueda por nombre
    Optional<ProductType> findBytypename(String type);

    //Búsqueda por id
	Optional<ProductType> findByIdproducttype(String producttype);

    //Busca todos
	List<ProductType> findAll();

}