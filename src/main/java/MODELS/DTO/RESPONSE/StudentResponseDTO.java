package MODELS.DTO.RESPONSE;

/**
 * Classe de DTO da entidade Student, para ser usada nas respostas da API
 * @author Camilly Chelest
 * @since 12/03/2025
 */

public record StudentResponseDTO(
    Long id,
    String name,
    String email,
    String password,
    String image
) {
}
