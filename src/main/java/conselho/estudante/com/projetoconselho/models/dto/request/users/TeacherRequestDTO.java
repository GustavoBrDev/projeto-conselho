package conselho.estudante.com.projetoconselho.models.dto.request.users;

import conselho.estudante.com.projetoconselho.models.entity.users.Teacher;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;


/**
 * Classe DTO para receber dados de criação e atualização de Teacher.
 * @author Alex Zastrow
 */
@Builder
public record TeacherRequestDTO(
        @NotNull
        @Positive
        Long register,
        @NotBlank
        String name,
        @NotBlank
        @Email
        String email,
        String password,
        String image
) {
    public Teacher convert() {
        return Teacher.builder()
                .register(this.register)
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .image(this.image)
                .build();
    }
}