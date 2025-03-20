package MODELS.ENTITY.DTO.RESPONSE;

import java.util.Date;

/**
 * Classe DTO para enviar dados de resposta relacionados ao Advisor
 * @author Alex Zastrow
 */
public class AdvisorResponseDTO {

    private Long id;
    private String name;
    private String email;
    private Long register;
    private String image;
    private Date createdAt;

    /*
     * Getters and Setters
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
