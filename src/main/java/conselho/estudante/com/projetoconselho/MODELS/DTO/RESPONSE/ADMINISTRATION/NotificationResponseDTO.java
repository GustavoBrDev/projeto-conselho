package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ADMINISTRATION;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public record NotificationResponseDTO(
        Long id,
        String message,
        Boolean isRead,
        Boolean isUrgent,
        Date createdAt
) {

}
