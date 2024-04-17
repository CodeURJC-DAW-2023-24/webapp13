package es.gualapop.backend.controller.api;

import es.gualapop.backend.model.Review;
import es.gualapop.backend.repository.ProductRepository;
import es.gualapop.backend.repository.ReviewRepository;
import es.gualapop.backend.repository.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import com.fasterxml.jackson.annotation.JsonView;

import es.gualapop.backend.model.Product;
import es.gualapop.backend.model.User;
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
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(defaultValue = "1") int page,
                                                        @RequestParam(defaultValue = "8") int size){
        Pageable pageable = PageRequest.of(page - 1, size); // Ajuste para que la página comience desde 1
        Page<Product> productPage = productService.getProductsPage(pageable);

        if (productPage.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(productPage.getContent());
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
        Optional<User> user = userService.findById(product.getOwner());
        if (product.getTitle() == null || product.getTitle().isEmpty() ||
        product.getAddress() == null || product.getAddress().isEmpty() ||
        product.getPrice() <= 0 || product.getDescription() == null || product.getDescription().isEmpty() ||
        product.getOwner() <= 0 || product.getProductType() <= 0 || user.isEmpty()) {
            return new ResponseEntity<>(product, HttpStatus.NOT_ACCEPTABLE);
        }
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

    @Operation(summary = "Get All electronic products")
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
    @GetMapping("/electronics")
    public ResponseEntity<List<Product>> getElectronics(){
        List<Product> products = productService.getProductsByProductType((long) 1);
        if (products.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(products);
        }
    }

    @Operation(summary = "Get All furniture products")
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
    @GetMapping("/furniture")
    public ResponseEntity<List<Product>> getFurniture(){
        List<Product> products = productService.getProductsByProductType((long) 2);
        if (products.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(products);
        }
    }

    @Operation(summary = "Get All clothing products")
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
    @GetMapping("/clothes")
    public ResponseEntity<List<Product>> getClothes(){
        List<Product> products = productService.getProductsByProductType((long) 3);
        if (products.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(products);
        }
    }

    @Operation(summary = "Get All book products")
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
    @GetMapping("/books")
    public ResponseEntity<List<Product>> getBooks(){
        List<Product> products = productService.getProductsByProductType((long) 4);
        if (products.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(products);
        }
    }

    @Operation(summary = "Get All sport products")
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
    @GetMapping("/sports")
    public ResponseEntity<List<Product>> getSports(){
        List<Product> products = productService.getProductsByProductType((long) 5);
        if (products.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(products);
        }
    }

    @Operation(summary = "Get All home and garden products")
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
    @GetMapping("/homegarden")
    public ResponseEntity<List<Product>> getHomeGarden(){
        List<Product> products = productService.getProductsByProductType((long) 6);
        if (products.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(products);
        }
    }

    @Operation(summary = "Get All toy products")
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
    @GetMapping("/toys")
    public ResponseEntity<List<Product>> getToys(){
        List<Product> products = productService.getProductsByProductType((long) 7);
        if (products.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(products);
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
    @GetMapping("/compras")
    public ResponseEntity<?> purchaseProduct(
            @RequestParam(name = "productID", required = true) Long productID,
            @RequestParam(name = "rating", required = false, defaultValue = "0.0") float rating) {

        Optional<Product> product = productRepository.findById(productID);
        if (product.isPresent()) {
            Product product1 = product.get();
            Long sellerID = product1.getOwner();

            if (rating > 0) {
                // Guarda la revisión solo si la calificación es mayor que 0
                Review review = new Review(rating, sellerID);
                reviewRepository.save(review);
            }

            Double price = product1.getPrice();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = authentication.getName();

            // Incrementa los ingresos del vendedor
            Optional<User> seller = userRepository.findById(product1.getOwner());
            seller.ifPresent(user -> {
                user.setIncome(user.getIncome() + price);
                userRepository.save(user);
            });

            // Aumenta los gastos del comprador
            Optional<User> buyer = userRepository.findByUsername(currentUsername);
            buyer.ifPresent(thisUser -> {
                thisUser.setExpense(thisUser.getExpense() + price);
                userRepository.save(thisUser);
            });

            // Elimina el producto de la base de datos después de la compra
            productRepository.deleteById(productID);

            return ResponseEntity.ok().body("Producto comprado: " + product.get());
        } else {
            // Si el producto no se encuentra
            return ResponseEntity.notFound().build();
        }
    }


}
