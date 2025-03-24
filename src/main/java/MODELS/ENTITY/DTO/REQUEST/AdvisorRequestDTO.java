package MODELS.ENTITY.DTO.REQUEST;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import MODELS.ENTITY.USERS.Advisor;

@Builder
public record AdvisorRequestDTO(
        @NotBlank String name,
        String image,  // Imagem não é obrigatória
        @NotBlank String email,
        @NotNull @Positive Long registration,
        @NotBlank String password
) {
    public Advisor convert() {
        return Advisor.builder()
                .name(this.name)
                .image(this.image)
                .email(this.email)
                .registration(this.registration)
                .password(this.password)  // A senha será criptografada no service
                .build();
    }
}