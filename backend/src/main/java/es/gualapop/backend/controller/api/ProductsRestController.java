package es.gualapop.backend.controller.api;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.gualapop.backend.model.Product;
import es.gualapop.backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@CrossOrigin
@RequestMapping("api/products")
public class ProductsRestController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Get New Eight Products")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the Products",
            content = {@Content(
                mediaType = "application/json"
            )}
        )
    })
    @JsonView(Product.Detailed.class)
    @GetMapping("/")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.getNewProducts();
        if (products.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(products);
        }
    }
}
