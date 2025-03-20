package conselho.estudante.com.projetoconselho.DTO.RESPONSE;

/**
 * Classe de transferencia de dados da entidade Subject
 * @author Camilly Chelest
 * @since 12/03/2025
 */

public record SubjectResponseDTO(
    Long id,
    String name,
    Integer workLoad
) {
}
