package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE;

import lombok.Builder;

import java.time.Instant;

/**
 * DTO de resposta para a entidade Chat
 * @param message a mensagem
 * @param isRead se a mensagem foi lida
 * @param isDeleted se a mensagem foi deletada
 * @param deletedAt instante em que a mensagem foi deletada
 * @author Gustavo Stinghen
 * @since 19/03/2025
 */
@Builder
public record ChatMessageResponseDTO(
    String message,
    Boolean isRead,
    Boolean isDeleted,
    Instant deletedAt
) {
}
