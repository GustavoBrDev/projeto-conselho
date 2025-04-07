package conselho.estudante.com.projetoconselho.models.entity.users;

import conselho.estudante.com.projetoconselho.models.dto.response.USERS.TechniqueResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.administration.Notification;
import jakarta.persistence.Entity;
import lombok.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Classe model da entidade Técnico da equipe pedagógica
 * @see User
 * @author Gustavo Stinghen
 * @since 10/03/2025
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Technique implements User {

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
     * Metodo para adicionar uma notificacao ao tecnico
     * @param notification a notificacao a ser adicionada
     * @return um booleano indicando se a notificacao foi adicionada. Se verdadeiro, a notificacao foi adicionada ao tecnico. Se falso, a notificacao nao foi adicionada ao tecnico
     * A notificacao nao pode ser adicionada se ela ja estiver na lista de notificacoes
     * @see Notification
     * @author Gustavo Stinghen
     * @since 20/03/2025
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
     * Metodo para remover uma notificacao ao tecnico
     * @param notification a notificacao a ser removida
     * @return um booleano indicando se a notificacao foi removida. Se verdadeiro, a notificacao foi removida ao tecnico. Se falso, a notificacao nao foi removida ao tecnico
     * A notificacao nao pode ser removida se ela nao estiver na lista de notificacoes
     * @see Notification
     * @author Gustavo Stinghen
     * @since 20/03/2025
     */
    public boolean removeNotification(Notification notification) {

        if (this.notifications.contains(notification)) {
            this.notifications.remove(notification);
            return true;
        } else {
            return false;
        }
    }


    /**
     * Converte a entidade Technique em um DTO de resposta TechniqueResponseDTO.
     *
     * @return Uma instância de TechniqueResponseDTO contendo os dados desta entidade.
     * @see TechniqueResponseDTO
     */
    public TechniqueResponseDTO toDTO() {
        return TechniqueResponseDTO.builder()
                .id(this.id)
                .name(this.name)
                .image(this.image)
                .email(this.email)
                .register(this.register)
                .password(this.password)
                .build();
    }
}
