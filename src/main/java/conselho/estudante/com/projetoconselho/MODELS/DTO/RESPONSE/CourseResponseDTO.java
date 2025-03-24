<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/DTO/RESPONSE/CourseResponseDTO.java
package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE;
========
package conselho.estudante.com.projetoconselho.DTO.RESPONSE;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/DTO/RESPONSE/CourseResponseDTO.java

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
