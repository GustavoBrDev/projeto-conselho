package MODELS.ENTITY.DTO.RESPONSE;

/**
 * DTO para representar a resposta após operações de criação, leitura ou atualização de uma entidade Advisor.
 */
public record AdvisorResponseDTO(
        Long id,          // Identificador único do orientador
        String name,     // Nome do orientador
        String image,    // Imagem associada ao orientador
        String email,     // Endereço de email do orientador
        Long registration // Número de matrícula do orientador
) {
}