package conselho.estudante.com.projetoconselho.models.dto.response.ADMINISTRATION;


import conselho.estudante.com.projetoconselho.models.entity.administration.Course;
import conselho.estudante.com.projetoconselho.models.entity.users.Representative;
import lombok.Builder;

/**
 * Convert to Basic Latin para responder com informações sobre a entidade Classe.
 * Esse DTO é usado para transferir dados de uma instância da entidade Classe para o cliente.
 *
 * @author joana voigt
 * @since 17/03/2025
 *
 * @see Course
 * @see Representative
 */
@Builder
public record ClasseResponseDTO(
        Long id,
        String name,
        String acronym,
        Course course,
        Boolean active
) {
}

