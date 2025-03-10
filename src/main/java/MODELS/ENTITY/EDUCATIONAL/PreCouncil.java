package MODELS.ENTITY.EDUCATIONAL;

import MODELS.ENTITY.ADMINISTRATION.Classe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Classe model da entidade PreCouncil (abstração para pre-council)
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see Council
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@MappedSuperclass
public abstract class PreCouncil {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Council council;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private boolean isFilled;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Classe classe;
}
