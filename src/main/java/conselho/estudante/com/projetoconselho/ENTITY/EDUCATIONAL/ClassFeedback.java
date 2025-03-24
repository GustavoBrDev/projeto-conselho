<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/ENTITY/EDUCATIONAL/ClassFeedback.java
package conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
========
package conselho.estudante.com.projetoconselho.ENTITY.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.ENTITY.ADMINISTRATION.Classe;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/ENTITY/EDUCATIONAL/ClassFeedback.java
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
}
