package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.USERS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * Classe DTO para receber dados de criação e atualização de Teacher.
 * @author Alex Zastrow
 */
@Data
public class TeacherRequestDTO {

    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @Email(message = "O email deve ser válido.")
    @NotBlank(message = "O email é obrigatório.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    private String password;

    @NotNull(message = "O cadastro é obrigatório.")
    private Long register;

    private String image;

    public Teacher convert () {
        return Teacher.builder()
                .name(name)
                .email(email)
                .password(password)
                .register(register)
                .image(image)
                .build();
    }
}