package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.ADMINISTRATION;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Subject;
import lombok.Builder;

/**
 * Classe de DTO da entidade Subject
 * @author Camilly Chelest
 * @since 12/03/2025
 */

@Builder
public record SubjectRequestDTO(
    String name,
    Integer workLoad ) {

    public Subject convert() {
        return Subject.builder()
            .name(this.name)
            .workLoad(this.workLoad)
            .build();
    }
}
