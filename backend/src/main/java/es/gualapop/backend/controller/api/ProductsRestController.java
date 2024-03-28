package es.gualapop.backend.controller.api;

import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import com.fasterxml.jackson.annotation.JsonView;

import es.gualapop.backend.model.Product;
import es.gualapop.backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



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

    @Operation(summary = "Create a Product")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Succesful Product creation",
            content = {@Content(
                mediaType = "application/json"
            )}
        ),
        @ApiResponse(
            responseCode = "406", 
            description = "Not Acceptable Post title exists", 
            content = @Content
        ) 
    })
    @JsonView(Product.Detailed.class)
    @PostMapping("/")
    public ResponseEntity<Product> registerProduct( @Parameter(description="Object Type Product") @RequestBody(required=false) Product product) throws IOException{
        if (product == null) {
            return new ResponseEntity<Product>(product,HttpStatus.NOT_ACCEPTABLE);
        }
        if (product.getTitle() == null || product.getTitle().isEmpty() ||
        product.getAddress() == null || product.getAddress().isEmpty() ||
        product.getPrice() <= 0 || product.getDescription() == null || product.getDescription().isEmpty() ||
        product.getOwner() <= 0 || product.getProductType() <= 0) {
            return new ResponseEntity<Product>(product,HttpStatus.NOT_ACCEPTABLE);
        }
        productService.save(product);
        Product productAux = productService.getProductById(product.getId());
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(location).body(productAux);
    }
    
    @Operation( summary = "Get Product by its id")
    @ApiResponses( value = {
        @ApiResponse(
            responseCode = "200",
            description = "Product Found",
            content = {@Content(
                mediaType = "application/json"
            )}
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Product not found",
            content = @Content
        )
    })
    @JsonView(Product.Detailed.class)
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct ( @Parameter(description="id of Product to be searched") @PathVariable int id) throws IOException{
        Optional<Product> product = productService.findById(id);
        if(product.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(product.get());
        }
    }
    
    @Operation(summary = "Delete a Product")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "201", 
					description = "Successful Product delete", 
					content = {@Content(
							mediaType = "application/json"
							)}
					),
			@ApiResponse(
					responseCode = "404", 
					description = "Product not found", 
					content = @Content
					) 
	})
	@JsonView(Product.Detailed.class)
	@DeleteMapping("/{id}")
	public ResponseEntity<Product> deleteProduct( @Parameter(description="id of Product to be searched") @PathVariable int id){
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()){
            productService.deleteProduct(product.get().getId());
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get a Image Product by its id")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "200", 
					description = "Found the Image Product", 
					content = {@Content(
							mediaType = "application/json"
							)}
					),
			@ApiResponse(
					responseCode = "404", 
					description = "Product not found", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "204", 
					description = "Image not found", 
					content = @Content
					)
	})
	@GetMapping("/{id}/image")
	public ResponseEntity<Object> getImage1( @Parameter(description="id of Product to be searched") @PathVariable int id) throws SQLException{
		Optional<Product> product = productService.findById(id);
		if(product.isPresent()) {
			if(product.get().getImageFile() != null) {
				Resource file = new InputStreamResource(product.get().getImageFile().getBinaryStream());
				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
						.contentLength(product.get().getImageFile().length())
						.body(file);
			}else {
				return ResponseEntity.noContent().build();
			}
		}else {
			return ResponseEntity.notFound().build();
		}
	}
}
