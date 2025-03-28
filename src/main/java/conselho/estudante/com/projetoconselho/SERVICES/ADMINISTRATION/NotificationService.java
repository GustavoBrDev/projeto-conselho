package conselho.estudante.com.projetoconselho.SERVICES.ADMINISTRATION;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.ADMINISTRATION.NotificationRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Notification;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.ADMINISTRATION.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Classe de serviço para a entidade {@link Notification}
 * @author Camilly Chelest
 * @since 18/03/2025
 * @see Notification
 */

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;

}
