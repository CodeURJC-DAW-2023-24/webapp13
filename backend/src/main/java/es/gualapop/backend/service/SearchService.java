package es.gualapop.backend.service;

import es.gualapop.backend.model.Product;
import es.gualapop.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    @Autowired
    private ProductRepository productRepository;

    public Page<Product> searchProducts(String query, Pageable pageable) {
        if (query == null || query.isEmpty()) {
            return productRepository.findAll(pageable);
        } else {
            return productRepository.findByTitleContainingIgnoreCaseOrTitleEqualsIgnoreCase(query, query, pageable);
        }
    }

}
