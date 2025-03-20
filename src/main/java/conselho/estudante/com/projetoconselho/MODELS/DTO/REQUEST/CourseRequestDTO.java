package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Course;
import lombok.Builder;

/**
 * Classe de DTO da entidade Course
 * @author Camilly Chelest
 * @since 12/03/2025
 */

@Builder
public record CourseRequestDTO(
        String name,
        String visualldentity,
        Integer workload,
        String level) {

    public Course convert() {
        return Course.builder()
                .name(this.name)
                .visualldentity(this.visualldentity)
                .workload(this.workload)
                .level(this.level)
                .build();
    }
}
