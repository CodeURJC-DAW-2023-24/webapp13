package es.gualapop.backend.service;

import org.springframework.stereotype.Service;

import es.gualapop.backend.model.Review;
import es.gualapop.backend.model.User;
import es.gualapop.backend.repository.ProductRepository;
import es.gualapop.backend.repository.ProductTypeRepository;
import es.gualapop.backend.repository.ReviewRepository;
import es.gualapop.backend.repository.UserRepository;

import java.io.IOException;
import java.sql.Blob;
import java.util.List;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Service
public class LoaderService {
    /*
    //@Autowired
    //private ProductRepository productRepository;

    //@Autowired
    //private ProductTypeRepository productTypeRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    public void Load() throws IOException{
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

       if(reviewRepository.findAll().isEmpty()) {
            reviewRepository.save(review1);
            reviewRepository.save(review2);
            reviewRepository.save(review3);
        }  

        if(userRepository.findAll().isEmpty()) {
            userRepository.save(user1);
            userRepository.save(user2);
        }

    }*/
}
