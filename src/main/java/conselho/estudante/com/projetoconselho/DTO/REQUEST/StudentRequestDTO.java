<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/DTO/REQUEST/StudentRequestDTO.java
package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST;


import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Student;
========
package conselho.estudante.com.projetoconselho.DTO.REQUEST;


import conselho.estudante.com.projetoconselho.ENTITY.USERS.Student;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/DTO/REQUEST/StudentRequestDTO.java
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;


/**
 * Classe model da entidade StudentRequestDTO
 * @author Camilly Chelest
 * @since 12/03/2025
 * @see Student
 */


@Builder
public record StudentRequestDTO(
        @NotNull
        @Positive
        Long registration,
        @NotNull
        boolean isRepresentative,
        @NotBlank //apenas para String
        String name,
        @NotBlank
        String email,
        @NotBlank
        String password,
        String image
) {


    public Student convert() {


        return Student.builder()
                .registration(this.registration)
                .isRepresentative(this.isRepresentative)
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .image(this.image)
                .build();
    }
}
