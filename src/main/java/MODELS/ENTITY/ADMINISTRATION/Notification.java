package MODELS.ENTITY.ADMINISTRATION;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
}
