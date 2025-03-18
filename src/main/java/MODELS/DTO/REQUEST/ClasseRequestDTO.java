package MODELS.DTO.REQUEST;

import MODELS.ENTITY.ADMINISTRATION.Classe;
import MODELS.ENTITY.ADMINISTRATION.Course;
import MODELS.ENTITY.USERS.Representative;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * DTO (Data Transfer Object) para a criação de novas instâncias de {@link Classe}.
 * Utilizado para encapsular os detalhes de dados necessários ao criar ou atualizar uma entidade Classe.
 *
 * @author joana voigt
 * @since 17/03/2025
 *
 * @see Classe
 * @see Course
 * @see Representative
 */
@Builder
public record ClasseRequestDTO(
        @NotBlank
        String name,
        @NotBlank
        String acronym,
        @NotNull
        Course course,
        @NotNull
        Representative representative,
        @NotNull
        Boolean active
) {
    /**
     * Converte este DTO em uma entidade {@link Classe}.
     *
     * @return Uma nova instância de Classe com os dados fornecidos neste DTO.
     */
    public Classe convert() {
        return Classe.builder()
                .name(this.name)
                .acronym(this.acronym)
                .course(this.course)
                .representative(this.representative)
                .active(this.active)
                .build();
    }
}
