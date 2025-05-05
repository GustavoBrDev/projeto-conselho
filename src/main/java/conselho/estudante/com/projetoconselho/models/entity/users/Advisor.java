package conselho.estudante.com.projetoconselho.models.entity.users;

import conselho.estudante.com.projetoconselho.models.dto.response.users.AdvisorResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.administration.Notification;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Classe model da entidade Orientador da equipe pedagógica
 * @see User
 * @author Gustavo Stinghen
 * @since 10/03/2025
 *
 * Atualizado em 13/03/2025
 * @author Gustavo Stinghen
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Advisor implements User {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Long register;

    @OneToMany
    private List<Notification> notifications;

    /**
     * Metodo para adicionar uma notificacao ao professor
     * @param notification a notificacao a ser adicionada
     * @return um booleano indicando se a notificacao foi adicionada. Se verdadeiro, a notificacao foi adicionada ao professor. Se falso, a notificacao nao foi adicionada ao professor
     * A notificacao nao pode ser adicionada se ela ja estiver na lista de notificacoes
     * @see Notification
     * @author Gustavo Stinghen
     * @since 09/04/2025
     */
    public boolean addNotification(Notification notification) {

        if (this.notifications.contains(notification)) {
            return false;
        } else {
            this.notifications.add(notification);
            return true;
        }
    }

    /**
     * Metodo para remover uma notificacao ao professor
     * @param notification a notificacao a ser removida
     * @return um booleano indicando se a notificacao foi removida. Se verdadeiro, a notificacao foi removida ao professor. Se falso, a notificacao nao foi removida ao professor
     * A notificacao nao pode ser removida se ela nao estiver na lista de notificacoes
     * @see Notification
     * @author Gustavo Stinghen
     * @since 09/04/2025
     */
    public boolean removeNotification(Notification notification) {

        if (this.notifications.contains(notification)) {
            this.notifications.remove(notification);
            return true;
        } else {
            return false;
        }
    }

    public AdvisorResponseDTO convert() {
        return AdvisorResponseDTO.builder()
                .id(this.id)
                .image(this.image)
                .name(this.name)
                .email(this.email)
                .register(this.register)
                .build();
    }
}
