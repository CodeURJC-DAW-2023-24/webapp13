package es.gualapop.backend.service;


import es.gualapop.backend.model.Review;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.Blob;
import java.util.List;

import es.gualapop.backend.model.Product;
import es.gualapop.backend.model.ProductType;
import es.gualapop.backend.model.User;
import es.gualapop.backend.repository.ProductRepository;
import es.gualapop.backend.repository.ProductTypeRepository;
import es.gualapop.backend.repository.ReviewRepository;
import es.gualapop.backend.repository.UserRepository;

@Service
public class DatabaseInizialiter {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductTypeRepository productTypeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostConstruct
    public void init() throws IOException {

        //user inizialite
        Resource imageUser1 = new ClassPathResource("/static/images/imgUser1.png");
        Resource imageUser2 = new ClassPathResource("/static/images/imgUser2.jpg");

        Blob imgU1 = BlobProxy.generateProxy(imageUser1.getInputStream(), imageUser1.contentLength());
        Blob imgU2 = BlobProxy.generateProxy(imageUser2.getInputStream(), imageUser2.contentLength());


        List<Integer> reviewList1 = List.of(1,2);
        List<Integer> reviewList2 = List.of(3);
        //    public User(String username, Blob userImg, String email, String encodedPassword, String fName, List<Integer> reviews, String... roles) {
        User user1 = new User("NoAdmin", imgU1, "noadmin@gmail.com", passwordEncoder.encode("1234"), "Non Admin User", reviewList1,"USER");
        User user2 = new User("AdminUser", imgU2, "admin@gmail.com",  passwordEncoder.encode("abc"), "Admin User", reviewList2, "USER","ADMIN");

        if(userRepository.findAll().isEmpty()) {
            userRepository.save(user1);
            userRepository.save(user2);
        }

        Long id1 = user1.getUserID();
        Long id2 = user2.getUserID();

        Review review1 = new Review(3, "Descripcion 1", id2, id1);
        Review review2 = new Review(4, "Descripcion 2", id2, id1);
        Review review3 = new Review(1, "Descripcion 3", id1, id2);

        ProductType pt1 = new ProductType("Electronica");

        ProductType pt2 = new ProductType("Muebles");

        ProductType pt3 = new ProductType("Ropa");

        ProductType pt4 = new ProductType("Libros");

        ProductType pt5 = new ProductType("Deportes");

        ProductType pt6 = new ProductType("Hogar y Jardin");

        ProductType pt7 = new ProductType("Juguetes");

        if(productTypeRepository.findAll().isEmpty()) {
            productTypeRepository.save(pt1);
            productTypeRepository.save(pt2);
            productTypeRepository.save(pt3);
            productTypeRepository.save(pt4);
            productTypeRepository.save(pt5);
            productTypeRepository.save(pt6);
            productTypeRepository.save(pt7);
        }

        Long electronica = pt1.getId();
        Long muebles = pt2.getId();
        Long ropa = pt3.getId();
        Long libros = pt4.getId();
        Long deportes = pt5.getId();
        Long hogar = pt6.getId();
        Long juguetes = pt7.getId();

        //product inizialite
        Product product1 = new Product("Zapatillas","Zapatillas nuevas baratas",id1,40, ropa);
        setProductImage(product1,"/static/images/zapas.jpg");

        Product product2 = new Product("Balon","Balon mundial 2010 magico",id1,10, deportes);
        setProductImage(product2,"/static/images/balon.jpg");

        Product product3 = new Product("Guitarra","Guitarra nueva sin uso",id1,50, hogar);
        setProductImage(product3,"/static/images/guitarra.jpg");

        Product product4 = new Product("Vestido","Vestido usado 2 veces",id1,15, ropa);
        setProductImage(product4,"/static/images/vestido.jpg");

        Product product5 = new Product("Coche","coche 40.000 km",id1,4000, electronica);
        setProductImage(product5,"/static/images/coche.jpg");

        //save all

        if(reviewRepository.findAll().isEmpty()) {
            reviewRepository.save(review1);
            reviewRepository.save(review2);
            reviewRepository.save(review3);
        }  

        if(productRepository.findAll().isEmpty()) {
            productRepository.save(product1);
            productRepository.save(product2);
            productRepository.save(product3);
            productRepository.save(product4);
            productRepository.save(product5);
        }



    }

    public void setProductImage (Product product, String classpathResource) throws IOException {
        product.setImage(true);
        Resource image = new ClassPathResource(classpathResource);
        product.setImageFile(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
    }
}
