package es.gualapop.backend.service;

import es.gualapop.backend.model.Product;
import es.gualapop.backend.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
            throw new IllegalArgumentException("The price must be greater than 0");
        }
        if (newProduct.getDescription() == null || newProduct.getDescription().isEmpty()) {
            newProduct.setDescription("No description");
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

    public List<Product> findByOwnerId(Long owner) {
        return productRepository.findByOwner(owner);
    }

    public List<Product> findByPriceBetween(float minPrice, float maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public double getPriceByIdProduct(long id){
        Optional<Product> product = productRepository.findById(id);
        return product.map(Product::getPrice).orElse(-1.0);

    }

    public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }

    public List<Product> getSimilarProducts(Long product, int pageNumber, int pageSize) {
        Optional<Product> p = productRepository.findById(product);
        Long productTypeId = p.map(Product::getProductType).orElse((long) 0);

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").descending());
        Page<Product> productsPage = productRepository.findProductsByProductTypeAndIdNot(productTypeId, product, pageable);

        return productsPage.getContent();
    }

    public List<Product> getProductsByType(Long productType, int pageNumber, int pageSize) {

        List<Product> productsPage = productRepository.findProductsByProductType(productType);

        return productsPage;
    }

    public Page<Product> getProductsPage(Pageable page) {
		return productRepository.findAll(page);
	}

    public List<Product> getNewProducts(){
		Page<Product> products = productRepository.findAll(PageRequest.of(0, 8, Sort.by("id").descending()));
		return products.getContent();
	}

    public List<Product> getProductsByProductType(Long productType){
		return productRepository.findProductsByProductType(productType);
	}

    public void save(Product product){
        productRepository.save(product);
    }
}
