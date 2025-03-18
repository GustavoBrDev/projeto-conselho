package MODELS.DTO.REQUEST;

import MODELS.ENTITY.USERS.Student;
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
            .registration(registration)
            .isRepresentative(isRepresentative)
            .name(name)
            .email(email)
            .password(password)
            .image(image)
            .build();
    }
}
