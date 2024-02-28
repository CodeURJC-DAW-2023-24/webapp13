package es.gualapop.backend.service;

import es.gualapop.backend.model.Product;
import es.gualapop.backend.model.User;
import es.gualapop.backend.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.hm.hafner.util.NoSuchElementException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Product not found"));
    }

    public Product createProduct(Product newProduct) {
        if (newProduct.getPrice() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero");
        }
        if (newProduct.getDescription() == null || newProduct.getDescription().isEmpty()) {
            newProduct.setDescription("Sin descripción");
        }
        return productRepository.save(newProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Product not found"));
    	productRepository.deleteById(product.getId());
    }

    public List<Product> findByProdName(String prodName) {
        return productRepository.findByTitle(prodName);
    }

    public Optional<Product> findByProdTypeId(Long prodTypeId) {
        return productRepository.findById(prodTypeId);
    }

    public List<Product> findByOwnerId(User owner) {
        return productRepository.findByOwner(owner);
    }

    public List<Product> findByPriceBetween(float minPrice, float maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }


    public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }
}
