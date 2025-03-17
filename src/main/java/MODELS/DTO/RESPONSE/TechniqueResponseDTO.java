package MODELS.DTO.RESPONSE;
/**
 * DTO para representar a resposta após operações de criação, leitura ou atualização de uma entidade Technique.
 *
 * @author Joana Voigt
 * @since 17/03/2025
 */
public record TechniqueResponseDTO(
        Long id,          // Identificador único da técnica.
        String name,      // Nome da técnica.
        String image,     // Imagem associada à técnica, podendo ser um URL ou caminho.
        String email,     // Endereço de email da técnica.
        Long register     // Número de registro da técnica.
) {
}
