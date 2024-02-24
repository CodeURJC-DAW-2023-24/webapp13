package es.gualapop.backend.repository;

import java.util.List;
import java.util.Optional;
import es.gualapop.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUsername(String username);
    List<User> findByFullName(String fullname);
    List<User> findByUserID(String userID);

    List<User> findByUserEmail(String userEmail);

    List<User> findByUserEmailAndPassword(String userEmail, String password);

    //Buscar a todos los usuarios
    List<User> findAll();

    //Buscar por nombre de usuario
    Optional<User> findByusername(String username);

}
