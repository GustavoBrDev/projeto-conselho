package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ADMINISTRATION;


import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Course;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Representative;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

