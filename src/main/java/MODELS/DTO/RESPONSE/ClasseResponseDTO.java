package MODELS.DTO.RESPONSE;

import MODELS.ENTITY.ADMINISTRATION.Course;
import MODELS.ENTITY.USERS.Representative;

/**
 * Convert to Basic Latin para responder com informações sobre {@link MODELS.ENTITY.ADMINISTRATION.Classe}.
 * Esse DTO é usado para transferir dados de uma instância da entidade Classe para o cliente.
 *
 * @author joana voigt
 * @since 17/03/2025
 *
 * @see MODELS.ENTITY.ADMINISTRATION.Classe
 * @see Course
 * @see Representative
 */
public record ClasseResponseDTO(
        Long id,
        String name,
        String acronym,
        Course course,
        Representative representative,
        Boolean active
) {
}
