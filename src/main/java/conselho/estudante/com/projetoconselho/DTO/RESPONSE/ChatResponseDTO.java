<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/DTO/RESPONSE/ChatResponseDTO.java
package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE;
========
package conselho.estudante.com.projetoconselho.DTO.RESPONSE;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/DTO/RESPONSE/ChatResponseDTO.java

import lombok.Builder;

import java.time.Instant;

@Builder
public record ChatResponseDTO (
    String message,
    Boolean isRead,
    Boolean isDeleted,
    Instant deletedAt
) {
}
