package es.gualapop.backend.service;


import es.gualapop.backend.model.Review;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.Blob;
import java.util.List;

import es.gualapop.backend.model.Product;
import es.gualapop.backend.model.User;
import es.gualapop.backend.repository.ProductRepository;
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
    @PostConstruct
    public void init() throws IOException {

        //user inizialite
        Resource imageUser1 = new ClassPathResource("/static/images/imgUser1.png");
        Resource imageUser2 = new ClassPathResource("/static/images/imgUser2.jpg");

        Blob imgU1 = BlobProxy.generateProxy(imageUser1.getInputStream(), imageUser1.contentLength());
        Blob imgU2 = BlobProxy.generateProxy(imageUser2.getInputStream(), imageUser2.contentLength());

        List<String> nonAdmin = List.of("USER");
        List<String> adminList = List.of("ADMIN");
        List<Integer> reviewList1 = List.of(1,2);
        List<Integer> reviewList2 = List.of(3);

        Review review1 = new Review((long) 1, 3, "Descripcion 1", (long) 2, (long) 1);
        Review review2 = new Review((long) 2, 4, "Descripcion 2", (long) 2, (long) 1);
        Review review3 = new Review((long) 3, 1, "Descripcion 3", (long) 1, (long) 2);

        User user1 = new User((long) 1, "NoAdmin", imgU1, "noadmin@gmail.com", "1234", "No Admin", nonAdmin, reviewList1);
        User user2 = new User((long) 2, "AdminUser", imgU2, "admin@gmail.com", "abc", "Admin User", adminList, reviewList2);

        //product inizialite
        Product product1 = new Product("Zapatillas","Zapatillas nuevas baratas",user1,40);
        setProductImage(product1,"/static/images/zapas.jpg");

        Product product2 = new Product("Balon","Balon mundial 2010 magico",user1,10);
        setProductImage(product2,"/static/images/balon.jpg");

        Product product3 = new Product("Guitarra","Guitarra nueva sin uso",user1,50);
        setProductImage(product3,"/static/images/guitarra.jpg");

        Product product4 = new Product("Vestido","Vestido usado 2 veces",user1,15);
        setProductImage(product4,"/static/images/vestido.jpg");

        Product product5 = new Product("Coche","coche 40.000 km",user1,4000);
        setProductImage(product5,"/static/images/coche.jpg");




        //save all

        if(reviewRepository.findAll().isEmpty()) {
            reviewRepository.save(review1);
            reviewRepository.save(review2);
            reviewRepository.save(review3);
        }  

        if(userRepository.findAll().isEmpty()) {
            userRepository.save(user1);
            userRepository.save(user2);
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
