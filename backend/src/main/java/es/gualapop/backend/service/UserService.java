package es.gualapop.backend.service;

import es.gualapop.backend.model.User;
import es.gualapop.backend.repository.UserRepository;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<String> getRolfromUser(Long userID) {
        // Get user by id
        User user = userRepository.findByUserID(userID).orElse(null);

        if (user != null) {
            return user.getRoles();
        } else {
            return null;
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

	public Boolean registerUser(User user, MultipartFile image) throws IOException {

		if(userRepository.existsUserByUsername(user.getUsername())) {
			return false;
		}else {
			if(image != null && image.getOriginalFilename() != "") {
				user.setUserImg(BlobProxy.generateProxy(image.getInputStream(), image.getSize()));
			} else {
                Resource imageUser1 = new ClassPathResource("/static/images/imgUser1.png");
                Blob imgU1 = BlobProxy.generateProxy(imageUser1.getInputStream(), imageUser1.contentLength());
                user.setUserImg(imgU1);
            }
            user.setEncodedPassword(encodePassword(user.getEncodedPassword()));
            userRepository.save(user);
		}
        return true;

	}
    public boolean checkPassword(String password, String validate){
        return password.equals(validate);
    }
    public String encodePassword(String password){
        return encoder.encode(password);
    }

}
