package conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION;

import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ADMINISTRATION.NotificationResponseDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertFalse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
