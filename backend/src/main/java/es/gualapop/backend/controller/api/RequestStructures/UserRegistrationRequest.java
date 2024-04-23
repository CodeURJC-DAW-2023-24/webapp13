package es.gualapop.backend.controller.api.RequestStructures;

import org.springframework.web.multipart.MultipartFile;

public class UserRegistrationRequest {
    private String name;
    private String username;
    private String password;
    private String repeatPassword;
    private String email;
    private MultipartFile image; // Para procesar el archivo de imagen, si es necesario

    // Getters y setters para los campos
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
