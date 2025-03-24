<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/DTO/REQUEST/SubjectRequestDTO.java
package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Subject;
========
package conselho.estudante.com.projetoconselho.DTO.REQUEST;

import conselho.estudante.com.projetoconselho.ENTITY.ADMINISTRATION.Subject;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/DTO/REQUEST/SubjectRequestDTO.java
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
