package es.gualapop.backend.controller.api;

import es.gualapop.backend.model.Review;
import es.gualapop.backend.repository.ProductRepository;
import es.gualapop.backend.repository.ReviewRepository;
import es.gualapop.backend.repository.UserRepository;
import es.gualapop.backend.service.ProductTypeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import com.fasterxml.jackson.annotation.JsonView;
import com.itextpdf.text.DocumentException;

import es.gualapop.backend.model.Product;
import es.gualapop.backend.model.User;
import es.gualapop.backend.service.PDFService;
import es.gualapop.backend.service.ProductService;
import es.gualapop.backend.service.SearchService;
import es.gualapop.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("api/products")
public class ProductsRestController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductTypeService productTypeService;
    @Autowired
    private UserService userService;
    @Autowired
    private SearchService searchService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PDFService pdfService;

    @Operation(summary = "Get Products")
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
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(defaultValue = "1") int page,
                                                        @RequestParam(defaultValue = "8") int size,
                                                        @RequestParam(required = false, defaultValue = "0") Long productType){
        if (productType == 0){
            Pageable pageable = PageRequest.of(page - 1, size); // Ajuste para que la página comience desde 1
            Page<Product> productPage = productService.getProductsPage(pageable);

            if (productPage.isEmpty()){
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok().body(productPage.getContent());
            }
        } else {
            List<Product> products = productService.getProductsByProductType(productType);
            if (products.isEmpty()){
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok().body(products);
            }
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
            return new ResponseEntity<>(product, HttpStatus.NOT_ACCEPTABLE);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Optional<User> user1 = userService.findByUsername(currentUsername);
        if (user1.isPresent()) {
            User user = user1.get();
            if (product.getTitle() == null || product.getTitle().isEmpty() ||
            product.getAddress() == null || product.getAddress().isEmpty() ||
            product.getPrice() <= 0 || product.getDescription() == null || product.getDescription().isEmpty() ||
            product.getProductType() <= 0) {
                return new ResponseEntity<>(product, HttpStatus.NOT_ACCEPTABLE);
            }
            product.setOwner(user.getUserID());
            productService.save(product);
            Product productAux = productService.getProductById(product.getId());

            // Construye la URL para el recurso de producto
            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            String locationUrl = baseUrl + "/products/" + product.getId(); // Por ejemplo, /products/{productID}

            // Agrega la cabecera "Location" con la URL del recurso creado
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", locationUrl);

            // Devuelve la respuesta con el código 201 y la cabecera "Location"
            return ResponseEntity.created(URI.create(locationUrl)).headers(headers).body(productAux);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
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
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
    public ResponseEntity<Product> deleteProduct(@PathVariable int id) {
        Optional<Product> product = productService.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        if (product.isPresent()) {
            Optional<User> opUser = userService.findById(product.get().getOwner());
            if (opUser.isPresent()) {
                User owner = opUser.get();

                // Obtener el primer rol del usuario autenticado
                String firstRole = getFirstRoleFromAuthentication(authentication);

                // Verificar si el usuario actual tiene permiso para eliminar el producto
                if (owner.getName().equals(currentUsername) || "ROLE_ADMIN".equals(firstRole)) {
                    productService.deleteProduct(product.get().getId());
                    return ResponseEntity.ok(product.get());
                } else {
                    // El usuario no es el propietario del producto ni tiene rol de ADMIN
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }
        }

        // Producto no encontrado o usuario no autorizado
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    // Método para obtener el primer rol del usuario autenticado
    private String getFirstRoleFromAuthentication(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            if (!authentication.getAuthorities().isEmpty()) {
                GrantedAuthority firstAuthority = authentication.getAuthorities().iterator().next();
                return firstAuthority.getAuthority();
            }
        }
        return null;
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

    @Operation(summary = "Create a Image Product by its id")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "201", 
					description = "Create the Image Product", 
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
	@PostMapping("/{id}/image")
	public ResponseEntity<Object> uploadImage1( @Parameter(description="id of Product to be searched") @PathVariable int id, @Parameter(description="Image 1 Product") @RequestParam() MultipartFile image) throws SQLException, IOException{
		Optional<Product> product = productService.findById(id);
		if(product.isPresent()) {
			if(image != null) {
				product.get().setImageFile(BlobProxy.generateProxy(image.getInputStream(), image.getSize()));
				product.get().setImage(true);
				productService.save(product.get());
				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
                String locationUrl = baseUrl + "/products/" + product.get().getId(); // Por ejemplo, /products/{productID}
                
                // Agrega la cabecera "Location" con la URL del producto actualizado
                HttpHeaders headers = new HttpHeaders();
                headers.add("Location", locationUrl);

                // Devuelve la respuesta con el código 201 y la cabecera "Location"
                return ResponseEntity.created(URI.create(locationUrl)).headers(headers).build();
			}else {
				return ResponseEntity.noContent().build();
			}
		}else {
			return ResponseEntity.notFound().build();
		}
	}

    @Operation(summary = "Get similar products")
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
    @GetMapping("/{id}/similar")
    public ResponseEntity<List<Product>> getSimilarProducts(@Parameter(description="id of Product to be searched") @PathVariable long id, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int pageSize){
        List<Product> products = productService.getSimilarProducts(id, page, pageSize);
        if (products.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(products);
        }
    }

    @Operation(summary = "Get products by type")
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
    @GetMapping("/type/{id}")
    public ResponseEntity<List<Product>> getProductsByType(@Parameter(description="id of Product to be searched") @PathVariable long id){
        List<Product> products = productService.getProductsByProductType(id);
        if (products.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(products);
        }
    }

    @Operation(summary = "Get search")
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
    @GetMapping("/search")
    public ResponseEntity<List<Product>> getSearch(@RequestParam String search,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "8") int size){
        // Crear objeto Pageable para la paginación
        Pageable pageable = PageRequest.of(page, size);
        
        // Realizar la búsqueda paginada
        Page<Product> products = searchService.searchProducts(search, pageable);
        
        // Verificar si se encontraron productos
        if (products.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(products.getContent());
        }
    }

    @JsonView(Product.Detailed.class)
    @GetMapping("/getCategories")
    public ResponseEntity<ArrayList<String>> getAllCategories(){
        return ResponseEntity.ok().body(productTypeService.getAllCategories());
    }

    @Operation(summary = "Purchase Product")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Product purchased",
            content = {@Content(
                mediaType = "application/json"
            )}
        )
    })

    @JsonView(Product.Detailed.class)
    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseProduct(
            @RequestParam(name = "productID", required = true) Long productID,
            @RequestParam(name = "rating", required = false, defaultValue = "0.0") float rating) {

        if (productID == null) {
            return ResponseEntity.badRequest().body("ProductID is required");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        Optional<Product> product = productRepository.findById(productID);

        if (product.isPresent()) {
            Product purchasedProduct = product.get();

            // Check if the current user is the owner of the product
            Optional<User> user1 = userRepository.findById(purchasedProduct.getOwner());
            if (user1.isPresent()){
                String owner = user1.get().getUsername();
                if (owner.equals(currentUsername)) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have permission to purchase this product");
                }
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have permission to purchase this product");
            }
            

            Double price = purchasedProduct.getPrice();

            // Save the review if a valid rating is provided
            if (rating > 0) {
                Review review = new Review(rating, purchasedProduct.getOwner());
                reviewRepository.save(review);
            }

            // Update seller's income
            Optional<User> seller = userRepository.findById(purchasedProduct.getOwner());
            seller.ifPresent(user -> {
                user.setIncome(user.getIncome() + price);
                userRepository.save(user);
            });

            // Update buyer's expenses
            Optional<User> buyer = userRepository.findByUsername(currentUsername);
            buyer.ifPresent(user -> {
                user.setExpense(user.getExpense() + price);
                userRepository.save(user);
            });

            // Delete the product from the database after purchase
            productRepository.delete(purchasedProduct);

            return ResponseEntity.ok().body("Product purchased: " + purchasedProduct);
        } else {
            // If the product is not found
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get PDF from purchase product")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "PDF generated",
            content = {@Content(
                mediaType = "application/json"
            )}
        )
    })
    @GetMapping("/pdf/{productId}")
    public ResponseEntity<byte[]> generatePDF(@PathVariable Long productId) {
        // Lógica para obtener el producto por ID y generar el PDF
        Product product = productService.getProductById(productId);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            byte[] pdfBytes = pdfService.generatePDF(product);
            // Devolver los bytes del PDF como respuesta
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (DocumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
