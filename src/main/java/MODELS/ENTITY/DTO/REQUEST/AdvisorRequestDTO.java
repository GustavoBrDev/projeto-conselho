package MODELS.ENTITY.DTO.REQUEST;

import java.util.Date;

/**
 * Classe DTO para receber dados de requisição relacionados ao Advisor
 * @author Alex Zastrow
 */
public class AdvisorRequestDTO {

    private String name;
    private String email;
    private String password;
    private Long register;
    private String image;

    /*
     * Getters and Setters
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRegister() {
        return register;
    }

    public void setRegister(Long register) {
        this.register = register;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
