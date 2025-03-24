<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/DTO/RESPONSE/TechniqueResponseDTO.java
package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE;
========
package conselho.estudante.com.projetoconselho.DTO.RESPONSE;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/DTO/RESPONSE/TechniqueResponseDTO.java
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
