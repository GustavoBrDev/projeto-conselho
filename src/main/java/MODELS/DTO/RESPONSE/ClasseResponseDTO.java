package MODELS.DTO.RESPONSE;

import MODELS.ENTITY.ADMINISTRATION.Course;
import MODELS.ENTITY.USERS.Representative;

public record ClasseResponseDTO(
        Long id,
        String name,
        String acronym,
        Course course,
        Representative representative,
        Boolean active
) {
}
