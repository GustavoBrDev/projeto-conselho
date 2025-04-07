package conselho.estudante.com.projetoconselho.models.dto.response.ADMINISTRATION;

import conselho.estudante.com.projetoconselho.models.entity.administration.Subject;
import lombok.Builder;

/**
 * Classe de transferencia de dados da entidade Subject
 * @author Camilly Chelest
 * @since 12/03/2025
 */

@Builder
public record SubjectResponseDTO(
    Long id,
    String name,
    Integer workLoad
) {

    public Subject convert() {
        return Subject.builder()
            .id(this.id)
            .name(this.name)
            .workLoad(this.workLoad)
            .build();
    }
}
