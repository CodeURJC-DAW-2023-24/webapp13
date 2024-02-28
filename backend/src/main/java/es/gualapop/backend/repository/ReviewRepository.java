package es.gualapop.backend.repository;

import java.util.List;

import es.gualapop.backend.model.Review;
import es.gualapop.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    //Búqueda por usuario
    public List<Review> findReviewBySellerID(Long iduser);

    //Búqueda por autor
    public List<Review> findReviewsByWriterID(Long iduser);

    //Borrar por usuario
    public Long deleteByReviewID(Long iduser);

    //Búsqueda entre rangos de valoración
    public List<Review> findReviewsByRatingBetween(float min, float max);

    List<Review> findAll();
}
