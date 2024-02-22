package es.gualapop.backend.repository;

import java.util.List;
import java.util.Optional;
import model.User;

public class UserRepository {

    //Buscar a todos los usuarios
    List<User> findAll();

    //Buscar por nombre de usuario
    Optional<User> findByusername(String username);

}
