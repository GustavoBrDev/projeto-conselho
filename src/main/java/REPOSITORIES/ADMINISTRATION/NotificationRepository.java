package REPOSITORIES.NOTIFICATION;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Retorna todas as notificações de forma paginada.
     * @param pageable informações de paginação
     * @return {@link Page<Notification>} página contendo notificações
     */
    Page<Notification> findAll(Pageable pageable);
}
