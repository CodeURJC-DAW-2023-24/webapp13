package es.gualapop.backend.repository;

import java.util.List;
import java.util.Optional;
import es.gualapop.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findByFullName(String fullname);
    Optional<User> findByUserID(Long userID);
    List<User> findByUserEmail(String userEmail);
    List<User> findAll();
    boolean existsById(Long userID);
    void deleteById(Long userID);

    Optional<User> findUserByUsername(String username);

    List<User> findByUserEmailAndEncodedPassword(String user, String password);
    boolean existsUserByUsername(String username);

}
