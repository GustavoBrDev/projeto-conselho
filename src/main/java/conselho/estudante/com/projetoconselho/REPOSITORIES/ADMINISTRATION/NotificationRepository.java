package conselho.estudante.com.projetoconselho.REPOSITORIES.ADMINISTRATION;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Notification;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Student;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Supervisor;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Technique;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Retorna todas as notificações de forma paginada.
     * @param pageable informações de paginação
     * @return {@link Page<Notification>} página contendo notificações
     */
    Page<Notification> findAll(Pageable pageable);

    /**
     * Busca todas as notificações de um estudante.
     *
     * @param student O estudante
     * @return Lista de notificações associadas ao estudante
     */
    List<Notification> findByStudent(Student student);

    /**
     * Busca todas as notificações de um técnico.
     *
     * @param technique O técnico
     * @return Lista de notificações associadas ao técnico
     */
    List<Notification> findByTechnique(Technique technique);

    /**
     * Busca todas as notificações de um supervisor.
     *
     * @param supervisor O supervisor
     * @return Lista de notificações associadas ao supervisor
     */
    List<Notification> findBySupervisor(Supervisor supervisor);
}
