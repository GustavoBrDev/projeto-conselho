package conselho.estudante.com.projetoconselho.SERVICES.ADMINISTRATION;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.ADMINISTRATION.NotificationRequestDTO;
import MODELS.EXCEPTIONS.NaoEncontradoException;
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

    /**
     * Cria uma nova {@link Notification}.
     *
     * @param requestDTO os dados da notificação a ser criada
     * @return {@link NotificationResponseDTO} a notificação criada
     */
    public NotificationResponseDTO create(NotificationRequestDTO requestDTO) {
        Notification notification = new Notification();
        notification.setMessage(requestDTO.message());
        notification.setIsUrgent(requestDTO.isUrgent());
        notification.setIsRead(false);
        notification.setCreatedAt(new Date());
        return repository.save(notification).convert();
    }

    /**
     * Edita o status de leitura de uma {@link Notification}.
     *
     * @param id o identificador da notificação
     * @param isRead o novo status de leitura da notificação
     * @return {@link NotificationResponseDTO} a notificação atualizada
     * @throws NaoEncontradoException se a notificação não for encontrada
     */
    public NotificationResponseDTO editRead(Long id, Boolean isRead) {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Notificação não encontrada"));
        notification.setIsRead(isRead);
        return repository.save(notification).convert();
    }

    /**
     * Lista todas as {@link Notification} com paginação.
     *
     * @param pageable as configurações de paginação
     * @return {@link Page<NotificationResponseDTO>} a página contendo as notificações encontradas
     */
    public Page<NotificationResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(Notification::convert);
    }

    /**
     * Busca uma {@link Notification} pelo seu ID.
     *
     * @param id o identificador da notificação
     * @return {@link NotificationResponseDTO} a notificação encontrada
     * @throws NaoEncontradoException se a notificação não for encontrada
     */
    public NotificationResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(Notification::convert)
                .orElseThrow(() -> new NaoEncontradoException("Notificação não encontrada"));
    }

    /**
     * Deleta uma {@link Notification} pelo seu ID.
     *
     * @param id o identificador da notificação
     * @throws NaoEncontradoException se a notificação não for encontrada
     */
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NaoEncontradoException("Notificação não encontrada");
        }
        repository.deleteById(id);
    }
}
