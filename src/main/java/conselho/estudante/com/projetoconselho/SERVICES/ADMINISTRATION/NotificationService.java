package conselho.estudante.com.projetoconselho.SERVICES.ADMINISTRATION;

import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ADMINISTRATION.NotificationResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Notification;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Student;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Supervisor;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Technique;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.ADMINISTRATION.NotificationRepository;
import conselho.estudante.com.projetoconselho.SERVICES.USERS.StudentService;
import conselho.estudante.com.projetoconselho.SERVICES.USERS.SupervisorService;
import conselho.estudante.com.projetoconselho.SERVICES.USERS.TECHNIQUE.TechniqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final StudentService studentService;
    private final TechniqueService techniqueService;
    private final SupervisorService supervisorService;

    /**
     * Cria uma nova notificação associada a um usuário.
     *
     * @param user     O usuário que receberá a notificação
     * @param message  A mensagem da notificação
     * @param isUrgent Se a notificação é urgente
     * @return {@link NotificationResponseDTO} A notificação criada
     */

    public NotificationResponseDTO create(User user, String message, Boolean isUrgent) {
        if (user == null || message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Usuário e mensagem são obrigatórios");
        }

        // Criando a notificação
        Notification notification = Notification.builder()
                .message(message)
                .isUrgent(isUrgent)
                .isRead(false)
                .createdAt(new Date())
                .build();

        if (user instanceof Student student) {

           studentService.addNotification(student.getId(), notification);

        } else if (user instanceof Technique technique) {

            techniqueService.addNotification(technique.getId(), notification);

        } else if (user instanceof Supervisor supervisor) {

            supervisorService.addNotification(supervisor.getId(), notification);
        }


        // Salva a notificação e retorna a resposta
        notification = notificationRepository.save(notification);
        return notification.convert();
    }

    /**
     * Atualiza uma notificação existente.
     *
     * @param notificationId O ID da notificação
     * @param newMessage     A nova mensagem da notificação
     * @param isRead         Se a notificação foi lida
     * @return {@link NotificationResponseDTO} A notificação atualizada
     */

    public NotificationResponseDTO update(Long notificationId, String newMessage, Boolean isRead) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NaoEncontradoException("Notificação não encontrada"));

        notification.setMessage(newMessage);
        notification.setIsRead(isRead);

        return notificationRepository.save(notification).convert();
    }

    /**
     * Remove uma notificação pelo ID.
     *
     * @param notificationId O ID da notificação a ser removida
     */
    
    public void delete(Long notificationId) {
        if (!notificationRepository.existsById(notificationId)) {
            throw new NaoEncontradoException("Notificação não encontrada");
        }
        notificationRepository.deleteById(notificationId);
    }



}
