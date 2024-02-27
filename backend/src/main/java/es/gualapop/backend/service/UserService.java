package es.gualapop.backend.service;

import es.gualapop.backend.model.User;
import es.gualapop.backend.repository.UserRepository;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService    {
	
    //Podemos hacerlo con autowired y creando el constructor. En este caso uso la segunda opcion
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.userRepository = repository;
        this.encoder = encoder;
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

    public List<String> getRolfromUser(Long userID) {
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
        List<User> users = userRepository.findByUserEmailAndEncodedPassword(user, password);
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
    
    public Optional<User> getUserId(long id){
		return userRepository.findByUserID(id);
	}
	
	public List<User> getAll(){
        return userRepository.findAll();
	}

	public User getUser(String username) {
		return (userRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("User not found")));
	}

    //comprobar si existe usuario registrado con ese username
	public void registerUsers(User user, MultipartFile image) throws IOException {

		if(userRepository.existsUserByUsername(user.getUsername())) {
			throw new NoSuchElementException("USERNAME IS TAKEN");
		}else {
			if(image != null) {
				user.setUserImg(BlobProxy.generateProxy(image.getInputStream(), image.getSize()));
			}
			user.setEncodedPassword(encoder.encode(user.getEncodedPassword()));
            user.setRoles("USER");
			userRepository.save(user);
		}

	}
}
