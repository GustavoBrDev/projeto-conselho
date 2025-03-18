package MODELS.DTO.RESPONSE;

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
