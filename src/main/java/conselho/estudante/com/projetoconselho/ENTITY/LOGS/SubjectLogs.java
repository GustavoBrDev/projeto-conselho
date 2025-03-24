<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/ENTITY/LOGS/SubjectLogs.java
package conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Subject;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
========
package conselho.estudante.com.projetoconselho.ENTITY.LOGS;

import conselho.estudante.com.projetoconselho.ENTITY.ADMINISTRATION.Subject;
import conselho.estudante.com.projetoconselho.ENTITY.USERS.User;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/ENTITY/LOGS/SubjectLogs.java
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * Classe model da entidade SubjectLogs
 * É uma subclasse de {@link Log}
 * É um documento do mongodb
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see Log, Subject
 *
 * Atualizado em 17/03/2025
 * Adicionado remoção automatica de logs
 * @author Gustavo Stinghen
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
@Builder
public class SubjectLogs implements Log {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private String id;

    private User actor;

    private Subject target;

    private String type;

    private Instant timestamp;

    @CreatedDate
    @Indexed( expireAfter = "60d" )
    private Date createdAt;

    private List<EditableItem> changes;

    /**
     * Adiciona um item ao log
     * @param change item a ser adicionado ao log em formato de {@link EditableItem}
     */
    public void addChange(EditableItem change) {
        changes.add(change);
    }
}
