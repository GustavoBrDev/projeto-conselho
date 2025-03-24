<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/DTO/RESPONSE/StudentResponseDTO.java
package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE;
========
package conselho.estudante.com.projetoconselho.DTO.RESPONSE;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/DTO/RESPONSE/StudentResponseDTO.java

import lombok.Builder;

/**
 * Classe de DTO da entidade Student, para ser usada nas respostas da API
 * @author Camilly Chelest
 * @since 12/03/2025
 *
 * Atualizado em 17/03/2025
 * Adicionado atributos isRepresentative e isHidden
 * @author Gustavo Stinghen
 */

@Builder
public record StudentResponseDTO(
    Long id,
    String name,
    String email,
    String password,
    String image,
    Boolean isRepresentative,
    Boolean isHidden
) {
}
