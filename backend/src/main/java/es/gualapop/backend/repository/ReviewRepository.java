package es.gualapop.backend.repository;

import java.util.List;
import java.util.Optional;
import model.User;
import model.Review;

public class ReviewRepository {

    //Búqueda por usuario
    public List<Review> findByiduser(User iduser);

    //Búqueda por autor
    public List<Review> findByidwriter(User iduser);

    //Borrar por usuario
    public Long deleteByIduser(User iduser);

    //Búsqueda entre rangos de valoración
    public List<Review> findByRatingRange(Integer min, Integer max);
}
