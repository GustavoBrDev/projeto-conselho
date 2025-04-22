package conselho.estudante.com.projetoconselho.models.dto.request.administration;

import conselho.estudante.com.projetoconselho.models.entity.administration.Course;
import conselho.estudante.com.projetoconselho.models.entity.administration.Shift;
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
        String level,
        Long shiftId) {

    public Course convert(Shift shift) {
        return Course.builder()
                .name(this.name)
                .visualIdentity(this.visualIdentity)
                .workLoad(this.workload)
                .level(this.level)
                .shift(shift)
                .build();
    }
}
