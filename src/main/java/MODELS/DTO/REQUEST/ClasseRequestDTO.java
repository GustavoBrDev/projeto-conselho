package MODELS.DTO.REQUEST;

import MODELS.ENTITY.ADMINISTRATION.Classe;
import MODELS.ENTITY.ADMINISTRATION.Course;
import MODELS.ENTITY.USERS.Representative;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

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
