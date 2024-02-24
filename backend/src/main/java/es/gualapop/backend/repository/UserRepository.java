package es.gualapop.backend.repository;

import java.util.List;
import java.util.Optional;
import es.gualapop.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUsername(String username);
    List<User> findByFullName(String fullname);
    Optional<User> findByUserID(Long userID); // Cambiado a Optional<User> para manejar el resultado de manera más apropiada
    List<User> findByUserEmail(String userEmail);

    List<User> findByUserEmailAndPassword(String userEmail, String password);

    //Buscar a todos los usuarios
    //Esto lo he trasladado a Service

    //Buscar por nombre de usuario
    Optional<User> findUserByUsername(String username);

}
