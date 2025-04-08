package conselho.estudante.com.projetoconselho.models.entity.logs;

import conselho.estudante.com.projetoconselho.models.entity.users.User;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * Classe model da entidade UserLogs
 * É uma subclasse de {@link Log}
 * É um documento do mongodb
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see Log, User
 *
 * Atualizado em 17/03/2025
 * Adicionado remoção automatica de logs
 * @author Gustavo Stinghen
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "userLogs")
@Builder
public class UserLogs implements Log {

    @Id
    private String id;

    @DBRef
    private User actor;

    @DBRef
    private User target;

    private String type;

    private Instant timestamp;

    @CreatedDate
    @Indexed ( expireAfter = "60d" )
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
