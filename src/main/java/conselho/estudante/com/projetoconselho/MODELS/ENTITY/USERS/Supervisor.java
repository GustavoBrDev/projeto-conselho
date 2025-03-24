package conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS;

import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS.SupervisorResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Course;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Notification;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * Classe model da entidade Supervisor
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see User
 *
 * Atualizado em 13/03/2025
 * @author Gustavo Stinghen
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Builder
public class Supervisor implements User {

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
    private List<Course> courses;

    @OneToMany
    private List<Notification> notifications;

    /**
     * Metodo para adicionar um curso ao supervisor
     * @param course o curso a ser adicionado
     * @return um booleano indicando se o curso foi adicionado. Se verdadeiro, o curso foi adicionado ao supervisor. Se falso, o curso nao foi adicionado ao supervisor
     * O curso nao pode ser adicionado se ele ja estiver na lista de cursos
     * @see Course
     */
    public boolean addCourse(Course course) {

        if (this.courses.contains(course)) {
            return false;
        } else {
            this.courses.add(course);
            return true;
        }

    }

    /**
     * Método para remover um curso ao supervisor
     * @param course o curso a ser removido
     * @return um booleano indicando se o curso foi removido. Se verdadeiro, o curso foi removido ao supervisor. Se falso, o curso nao foi removido ao supervisor
     * O curso nao pode ser removido se ele nao estiver na lista de cursos
     * @see Course
     */
    public boolean removeCourse(Course course) {

        if (this.courses.contains(course)) {
            this.courses.remove(course);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodo para adicionar uma notificacao ao supervisor
     * @param notification a notificacao a ser adicionada
     * @return um booleano indicando se a notificacao foi adicionada. Se verdadeiro, a notificacao foi adicionada ao supervisor. Se falso, a notificacao nao foi adicionada ao supervisor
     * A notificacao nao pode ser adicionada se ela ja estiver na lista de notificacoes
     * @see Notification
     * @author Gustavo Stinghen
     * @since 20/03/2025
     */
    public  boolean addNotification(Notification notification) {

        if (this.notifications.contains(notification)) {
            return false;
        } else {
            this.notifications.add(notification);
            return true;
        }
    }

    /**
     * Metodo para remover uma notificacao ao supervisor
     * @param notification a notificacao a ser removida
     * @return um booleano indicando se a notificacao foi removida. Se verdadeiro, a notificacao foi removida ao supervisor. Se falso, a notificacao nao foi removida ao supervisor
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

    public SupervisorResponseDTO convert() {
        return SupervisorResponseDTO.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .image(this.image)
                .register(this.register.toString())
                .build();
    }
}
