package es.gualapop.backend.service;

import es.gualapop.backend.model.Product;
import es.gualapop.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    @Autowired
    private ProductRepository productRepository;
    public List<Product> searchProducts(String query) {
        // Verificar si la consulta es nula o vacía
        if (query == null || query.isEmpty()) {
            // Si la consulta está vacía, devolver todos los productos
            return productRepository.findAll();
        } else {
            // Si la consulta no está vacía, buscar productos según varios criterios
            return productRepository.findByTitleContainingIgnoreCaseOrTitleEqualsIgnoreCase(query, query);
        }
    }

}
