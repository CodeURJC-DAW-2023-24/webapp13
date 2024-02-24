package es.gualapop.backend.service;

import es.gualapop.backend.model.User;
import es.gualapop.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService    {

    //Podemos hacerlo con autowired y creando el constructor. En este caso uso la segunda opcion
    private final UserRepository userRepository;

    public UserService(UserRepository repository) {
        this.userRepository = repository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Optional<User> findById(Long userID) {
        return userRepository.findByUserID(userID);
    }
    public List<User> findByUserEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail);
    }
    public List<User> findByFullName(String fullname) {
        return userRepository.findByFullName(fullname);
    }
    public List<User> findByUserName(String username) {
        return userRepository.findByUsername(username);
    }


    public List<Integer> getRolfromUser(Long userID) {
        // Obtener el usuario por su ID
        User user = userRepository.findByUserID(userID).orElse(null);

        if (user != null) {
            // Devolver los roles del usuario si se encuentra
            return user.getRoles();
        } else {
            // Manejar el caso donde no se encuentra el usuario
            return null; // O lanzar una excepción adecuada según sea necesario
        }
    }

    public boolean validateUserAndPassword(String user, String password) {
        List<User> users = userRepository.findByUserEmailAndPassword(user, password);
        return !users.isEmpty();
    }


    public void delete(long id) {
        userRepository.deleteById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean exist(long id) {
        return userRepository.existsById(id);
    }
}
