package MODELS.ENTITY.DTO.REQUEST;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

/**
 * DTO (Data Transfer Object) para representar uma solicitação de dados ao criar ou atualizar uma entidade Advisor.
 */
@Builder
public record AdvisorRequestDTO(
        @NotBlank
        String name,
        @NotBlank
        String image,
        @NotBlank
        String email,
        @NotNull
        @Positive
        Long registration
) {
    /**
     * Converte este DTO em uma entidade `Advisor`.
     * @return Uma nova instância de `Advisor` com os dados presentes neste DTO.
     */
    public Advisor convert() {
        return Advisor.builder()
                .name(this.name)
                .image(this.image)
                .email(this.email)
                .registration(this.registration)
                .build();
    }
}