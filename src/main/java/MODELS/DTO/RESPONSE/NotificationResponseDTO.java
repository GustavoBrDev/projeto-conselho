package MODELS.DTO.RESPONSE;

import MODELS.ENTITY.ADMINISTRATION.Notification;
import lombok.Builder;

import java.util.Date;

/**
 * Classe model da entidade Notificação
 * @author Camilly Chelest
 * @since 18/03/2025
 * @see Notification
 */

@Builder
public record NotificationResponseDTO(
        Long id,
        String message,
        boolean isRead,
        boolean isUrgent,
        Date createdAt
) {

}
