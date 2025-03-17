package MODELS.DTO.REQUEST;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

/**
 * DTO (Data Transfer Object) para representar uma solicitação de dados ao criar ou atualizar uma entidade Technique.
 *
 * @author Joana Voigt
 * @since 17/03/2025
 *
 * @see Technique
 */
@Builder
public record TechniqueRequestDTO(
        @NotBlank
        String name,
        @NotBlank
        String image,
        @NotBlank
        String email,
        @NotNull
        @Positive
        Long register
) {
        /**
         * Converte este DTO em uma entidade `Technique`.
         * @return Uma nova instância de `Technique` com os dados presentes neste DTO.
         * @see Technique
         */
        public Technique convert() {
                return Technique.builder()
                        .name(this.name)
                        .image(this.image)
                        .email(this.email)
                        .register(this.register)
                        .build();
        }
}