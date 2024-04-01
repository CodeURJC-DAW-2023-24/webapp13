package es.gualapop.backend.service;


import es.gualapop.backend.model.*;
import es.gualapop.backend.repository.*;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.Blob;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    @Autowired
    private ReportRepository reportRepository;
    
    @PostConstruct
    public void init() throws IOException {

        //user inizialite
        Resource imageUser1 = new ClassPathResource("/static/images/imgUser1.png");
        Resource imageUser2 = new ClassPathResource("/static/images/imgUser2.jpg");
        Resource imageUser3 = new ClassPathResource("/static/images/userIMG.png");

        Blob imgU1 = BlobProxy.generateProxy(imageUser1.getInputStream(), imageUser1.contentLength());
        Blob imgU2 = BlobProxy.generateProxy(imageUser2.getInputStream(), imageUser2.contentLength());
        Blob imgU3 = BlobProxy.generateProxy(imageUser3.getInputStream(), imageUser3.contentLength());


        List<Integer> reviewList1 = List.of(1,2);
        List<Integer> reviewList2 = List.of(3);

        User user1 = new User("NoAdmin", imgU1, "noadmin@gmail.com", passwordEncoder.encode("1234"), "Non Admin User", reviewList1,"USER");
        User user2 = new User("AdminUser", imgU2, "admin@gmail.com",  passwordEncoder.encode("abc"), "Admin User", reviewList2, "USER","ADMIN");
        User user3 = new User("User", imgU3, "user@gmail.com",  passwordEncoder.encode("user"), "User", reviewList2, "USER");




        if(userRepository.findAll().isEmpty()) {
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
        }

        Long id1 = user1.getUserID();
        Long id2 = user2.getUserID();
        Long id3 = user3.getUserID();

        Review review1 = new Review(3, id1);
        Review review2 = new Review(4, id1);
        Review review3 = new Review(1, id3);

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

        Product product6 = new Product("Silla","Silla Gamer",id3,150, muebles);
        setProductImage(product6,"/static/images/silla.jpg");

        Product product7 = new Product("El principe de la niebla","Libro El principe de la niebla de carlos ruiz zafón",id3,10, libros);
        setProductImage(product7,"/static/images/libro.png");

        Product product8 = new Product("Gormiti","Gorm, el señor de la tierra gobernador de el reino de la tierra de los gormiti",id3,300, juguetes);
        setProductImage(product8,"/static/images/gormiti.jpg");

        Product product9 = new Product("Nintendo DS","Nintendo DS Lite Portatil",id3,400, electronica);
        setProductImage(product9,"/static/images/nintendo.png");

        Product product10 = new Product("Gnomo de Jardín","Gnomo de jardín de la película de blancanieves",id3,20, hogar);
        setProductImage(product10,"/static/images/gnomo.png");

        Product product11 = new Product("Lego","Nave de lego star wars",id3,80, juguetes);
        setProductImage(product11,"/static/images/lego.jpg");

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
            productRepository.save(product6);
            productRepository.save(product7);
            productRepository.save(product8);
            productRepository.save(product9);
            productRepository.save(product10);
            productRepository.save(product11);
        }

        LocalDate fechaActual = LocalDate.now();
        // Formatear la fecha en el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fechaActual.format(formatter);

        Report report1 = new Report(user1.getUserID(), "Usuario Fraudulento","ASDAKJSDKJ",user3.getUserID(),fechaFormateada);
        Report report2 = new Report(user1.getUserID(), "Usuario Fraudulento","ASDAKJSDKJ",user3.getUserID(),fechaFormateada);
        Report report3 = new Report(user1.getUserID(), "Usuario Fraudulento","ASDAKJSDKJ",user3.getUserID(),fechaFormateada);
        Report report4 = new Report(user1.getUserID(), "Usuario Fraudulento","ASDAKJSDKJ",user3.getUserID(),fechaFormateada);
        Report report5 = new Report(user1.getUserID(), "Usuario Fraudulento","ASDAKJSDKJ",user3.getUserID(),fechaFormateada);
        Report report6 = new Report(user1.getUserID(), "Usuario Fraudulento","ASDAKJSDKJ",user3.getUserID(),fechaFormateada);
        Report report7 = new Report(user1.getUserID(), "Usuario Fraudulento","ASDAKJSDKJ",user3.getUserID(),fechaFormateada);


        if (reportRepository.findAll().isEmpty()){
            reportRepository.save(report1);
            reportRepository.save(report2);
            reportRepository.save(report3);
            reportRepository.save(report4);
            reportRepository.save(report5);
            reportRepository.save(report6);
            reportRepository.save(report7);

        }

    }

    public void setProductImage (Product product, String classpathResource) throws IOException {
        product.setImage(true);
        Resource image = new ClassPathResource(classpathResource);
        product.setImageFile(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
    }
}
