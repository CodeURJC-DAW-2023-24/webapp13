package es.gualapop.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.gualapop.backend.model.Product;
import es.gualapop.backend.service.ProductService;

@RestController
@CrossOrigin
public class AjaxController {
    @Autowired
    private ProductService productService;
    @GetMapping("/products/moreProducts")
	private Page<Product> getMoreProducts(Pageable page){
		return productService.getProductsPage(page);
	}

    @GetMapping("/products/getMoreProductsPage")
	private Page<Product> getMoreProductsPage(Pageable page){
        try {
            return productService.getProductsPage(page);
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Puedes manejar la excepción de manera adecuada según tu lógica
        }
	}

}
