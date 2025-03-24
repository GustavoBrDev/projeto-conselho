<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/ENTITY/ADMINISTRATION/Notification.java
package conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION;
========
package conselho.estudante.com.projetoconselho.ENTITY.ADMINISTRATION;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/ENTITY/ADMINISTRATION/Notification.java

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
