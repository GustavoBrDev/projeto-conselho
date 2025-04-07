package conselho.estudante.com.projetoconselho.models.dto.response.ADMINISTRATION;
import lombok.Builder;

import java.util.Date;

@Builder
public record NotificationResponseDTO(
        Long id,
        String message,
        Boolean isRead,
        Boolean isUrgent,
        Date createdAt
) {

}
