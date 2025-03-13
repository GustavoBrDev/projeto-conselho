package MODELS.ENTITY.ADMINISTRATION;

import MODELS.ENTITY.USERS.RegularUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe model da entidade Notificação
 * @author Camilly Chelest
 * @since 12/03/2025
 * @see RegularUser
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Notification {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Column(nullable = false)
    private RegularUser user;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private Boolean IsRead;

    @Column(nullable = false)
    private Boolean IsUrgent;

}
