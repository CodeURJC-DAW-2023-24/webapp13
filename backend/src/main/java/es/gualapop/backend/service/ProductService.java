package es.gualapop.backend.service;

import es.gualapop.backend.model.Product;
import es.gualapop.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Product> getAllProducts() {
        return productRepository.findAll();
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

    public List<Product> findByTitle(String title) {
        return productRepository.findByTitle(title);
    }

    /*public List<Product> findByProdTypeId(int prodTypeId) {
        return productRepository.findByProdTypeId(prodTypeId);
    }*/

    public List<Product> findById(long Id) {
        return productRepository.findById(Id);
    }

    public List<Product> findByPriceBetween(float minPrice, float maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

}
