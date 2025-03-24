<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/ENTITY/EDUCATIONAL/ItemFeedback.java
package conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL;
========
package conselho.estudante.com.projetoconselho.ENTITY.EDUCATIONAL;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/ENTITY/EDUCATIONAL/ItemFeedback.java

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Classe model da entidade Feedback de item
 * @author Gustavo Stinghen
 * @since 10/03/2025
 *
 * Atualizado em 13/03/2025
 * @author Gustavo Stinghen
 *
 * Atualizado em 17/03/2025
 * Utilização de uma interface
 * @author Gustavo Stinghen
 * @see Feedback
 */
@EqualsAndHashCode()
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ItemFeedback implements Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date createdAt;

    @ManyToOne
    private Council council;

    private String text;

    private String item;
}
