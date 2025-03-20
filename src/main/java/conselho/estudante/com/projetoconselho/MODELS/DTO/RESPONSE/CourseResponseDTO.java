package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE;

/**
 * Classe de transferencia de dados da entidade Course
 * @author Camilly Chelest
 * @since 12/03/2025
 */

public record CourseResponseDTO(
        Long id,
        String name,
        String visualldentity,
        Integer workload,
        String level ) {
}
