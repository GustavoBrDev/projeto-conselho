package conselho.estudante.com.projetoconselho.models.dto.request.ADMINISTRATION;

import conselho.estudante.com.projetoconselho.models.entity.administration.Course;
import lombok.Builder;

/**
 * Classe de DTO da entidade Course
 * @author Camilly Chelest
 * @since 12/03/2025
 */

@Builder
public record CourseRequestDTO(
        String name,
        String visualIdentity,
        Integer workload,
        String level) {

    public Course convert() {
        return Course.builder()
                .name(this.name)
                .visualIdentity(this.visualIdentity)
                .workLoad(this.workload)
                .level(this.level)
                .build();
    }
}
