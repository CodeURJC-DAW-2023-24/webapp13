package es.gualapop.backend.service;

import es.gualapop.backend.model.Product;
import es.gualapop.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    @Autowired
    private ProductRepository productRepository;

    public Page<Product> searchProducts(String query, Pageable pageable) {
        if (query == null || query.isEmpty()) {
            // Si la consulta está vacía, devolver todos los productos paginados
            return productRepository.findAll(pageable);
        } else {
            // Si la consulta no está vacía, buscar productos según el título paginados
            return productRepository.findByTitleContainingIgnoreCaseOrTitleEqualsIgnoreCase(query, query, pageable);
        }
    }

}
