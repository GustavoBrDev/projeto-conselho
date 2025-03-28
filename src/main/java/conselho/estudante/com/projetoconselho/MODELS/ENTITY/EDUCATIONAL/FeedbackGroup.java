package conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL.FeedbackGroupResponseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.chrono.Chronology;
import java.util.Date;

/**
 * Classe model da entidade Feedback de grupo
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see PersonalFeedback, ClassFeedback
 *
 * Atualizado em 13/03/2025
 * @author Gustavo Stinghen
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Builder
public class FeedbackGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date date;

    @OneToOne
    private PersonalFeedback personalFeedback;

    @ManyToOne
    private ClassFeedback classFeedback;

    public FeedbackGroupResponseDTO convert() {  /*Aqui na Entity quando fizer o metodo convert, tem q ser ResponseDTO*/
        return FeedbackGroupResponseDTO.builder()
                .date(this.date)
                .personalFeedback(this.personalFeedback.convert())
                .classFeedback(this.classFeedback.convert())
                .build();
    }

}
