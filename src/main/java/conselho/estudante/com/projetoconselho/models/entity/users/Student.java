package conselho.estudante.com.projetoconselho.models.entity.users;

import conselho.estudante.com.projetoconselho.models.dto.response.USERS.StudentResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.administration.Classe;
import conselho.estudante.com.projetoconselho.models.entity.administration.Notification;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * Classe model da entidade Aluno
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see User
 *
 * Atualizado em 13/03/2025
 * @author Gustavo Stinghen
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Student implements User {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Long registration;

    @Column(nullable = false)
    private Boolean isRepresentative;

    @Column(nullable = false)
    private Boolean isHidden;

    @ManyToMany(mappedBy = "students")
    private List<Classe> classes;

    @OneToMany
    private List<Notification> notifications;

    /**
     * Método para adicionar uma classe ao aluno
     * @param classe a classe a ser adicionada em formato de {@link Classe}
     * @return um booleano indicando se a classe foi adicionada. Se verdadeiro, a classe foi adicionada ao aluno. Se falso, a classe nao foi adicionada ao aluno
     * A classe nao pode ser adicionada se ela ja estiver na lista de classes
     * @see Classe
     */
    public boolean addClasse(Classe classe) {

        if (this.classes.contains(classe)) {
            return false;
        } else {
            this.classes.add(classe);
            return true;
        }

    }

    /**
     * Metodo para remover uma classe ao aluno
     * @param classe a classe a ser removida em formato de {@link Classe}
     * @return um booleano indicando se a classe foi removida. Se verdadeiro, a classe foi removida ao aluno. Se falso, a classe nao foi removida ao aluno
     * A classe nao pode ser removida se ela nao estiver na lista de classes
     * @see Classe
     */
    public boolean removeClasse(Classe classe) {

        if (this.classes.contains(classe)) {
            this.classes.remove(classe);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodo para adicionar uma notificacao ao aluno
     * @param notification a notificacao a ser adicionada em formato de {@link Notification}
     * @return um booleano indicando se a notificacao foi adicionada. Se verdadeiro, a notificacao foi adicionada ao aluno. Se falso, a notificacao nao foi adicionada ao aluno
     * A notificacao nao pode ser adicionada se ela ja estiver na lista de notificacoes
     * @see Notification
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
     * Metodo para remover uma notificacao ao aluno
     * @param notification a notificacao a ser removida em formato de {@link Notification}
     * @return um booleano indicando se a notificacao foi removida. Se verdadeiro, a notificacao foi removida ao aluno. Se falso, a notificacao nao foi removida ao aluno
     * A notificacao nao pode ser removida se ela nao estiver na lista de notificacoes
     * @see Notification
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
     * Metodo para converter um aluno para um DTO de aluno
     * @return um DTO de aluno
     */
    public StudentResponseDTO convert() {
        return StudentResponseDTO.builder()
                .id(id)
                .image(image)
                .name(name)
                .email(email)
                .password(password)
                .isRepresentative(isRepresentative)
                .isHidden(isHidden)
                .build();
    }
}