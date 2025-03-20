package MODELS.ENTITY.ADMINISTRATION;

import MODELS.DTO.RESPONSE.NotificationResponseDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 * Classe model da entidade Notificação
 * @author Gustavo Stinghen
 * @since 13/03/2025
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Getter
@Setter
@Builder
public class Notification {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    public Boolean isRead;

    @Column(nullable = false)
    private Boolean isUrgent;

    @Column(nullable = false)
    private Date createdAt;

    public NotificationResponseDTO convert() {
        return NotificationResponseDTO.builder()
                .id(id)
                .message(message)
                .isRead(isRead)
                .isUrgent(isUrgent)
                .createdAt(createdAt)
                .build();
    }
}
