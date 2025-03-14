package MODELS.ENTITY.EDUCATIONAL;

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
 */
@EqualsAndHashCode()
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ItemFeedback {

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
