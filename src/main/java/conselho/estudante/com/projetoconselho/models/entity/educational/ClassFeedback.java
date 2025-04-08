package conselho.estudante.com.projetoconselho.models.entity.educational;

import conselho.estudante.com.projetoconselho.models.dto.response.educational.ClassFeedbackResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.administration.Classe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Classe model da entidade Feedback de turma
 * @author Gustavo Stinghen
 * @since 13/03/2025
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class ClassFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date createdAt;

    @ManyToOne
    private Council council;

    @ManyToOne
    private Classe classe;

    @Column(nullable = false)
    private String text;

    public ClassFeedbackResponseDTO convert() {
        return ClassFeedbackResponseDTO.builder()
                .id(id)
                .council(council.toDTO())
                .classe(classe.toDTO())
                .createdAt(createdAt)
                .text(text)
                .build();
    }
}
