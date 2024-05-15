package es.gualapop.backend.service;

import es.gualapop.backend.model.Product;
import es.gualapop.backend.model.ProductType;
import es.gualapop.backend.repository.ProductRepository;
import es.gualapop.backend.repository.ProductTypeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductTypeService {

    private final ProductTypeRepository productTypeRepository;

    public ProductTypeService(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }
    public ArrayList<String> getAllCategories() {
        List<ProductType> types = productTypeRepository.findAll();
        ArrayList<String> categories = new ArrayList<>();

        for(ProductType type : types) {
            categories.add(type.getType());
        }

        return categories;
    }
}
