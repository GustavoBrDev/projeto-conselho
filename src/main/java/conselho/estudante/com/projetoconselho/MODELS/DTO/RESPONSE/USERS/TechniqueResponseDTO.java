package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS;
/**
 * DTO para representar a resposta após operações de criação, leitura ou atualização de uma entidade Technique.
 *
 * @author Joana Voigt
 * @since 17/03/2025
 */
public record TechniqueResponseDTO(
        Long id,
        String name,
        String image,
        String email,
        Long register
) {
}
