package conselho.estudante.com.projetoconselho.services.administration;

import conselho.estudante.com.projetoconselho.models.dto.response.administration.NotificationResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.administration.Notification;
import conselho.estudante.com.projetoconselho.models.entity.users.*;
import conselho.estudante.com.projetoconselho.models.exceptions.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.repositories.administration.NotificationRepository;
import conselho.estudante.com.projetoconselho.services.EmailService;
import conselho.estudante.com.projetoconselho.services.users.AdvisorService;
import conselho.estudante.com.projetoconselho.services.users.StudentService;
import conselho.estudante.com.projetoconselho.services.users.SupervisorService;
import conselho.estudante.com.projetoconselho.services.users.technique.TechniqueService;
import conselho.estudante.com.projetoconselho.services.users.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Serviço para gerenciar operações relacionadas à entidade {@link Notification}.
 *
 * @author Camilly Chelest
 * @since 31/03/2025
 *
 * @see Notification
 *
 * Atualizado em 01/04/2025
 * Conexão com o EmailService para envio de emails
 * Atualização de lógica interna
 * @author Gustavo Stinghen
 */

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final StudentService studentService;
    private final TechniqueService techniqueService;
    private final AdvisorService advisorService;
    private final EmailService emailService;
    private final SupervisorService supervisorService;
    private final TeacherService teacherService;

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

           if ( isUrgent ) {
               emailService.sendAlertEmail( student.getEmail(), notification );
           }

        } else if (user instanceof Technique technique) {

            techniqueService.addNotification(technique.getId(), notification);

            if ( isUrgent ) {
                emailService.sendAlertEmail( technique.getEmail(), notification );
            }

        } else if (user instanceof Supervisor supervisor) {

            supervisorService.addNotification(supervisor.getId(), notification);

            if ( isUrgent ) {
                emailService.sendAlertEmail( supervisor.getEmail(), notification );
            }

        } else if ( user instanceof Advisor advisor) {

            // advisorService.addNotification(advisor.getId(), notification);

            if ( isUrgent ) {
                emailService.sendAlertEmail( advisor.getEmail(), notification );
            }

        } else if ( user instanceof Teacher teacher) {

            // teacherService.addNotification(teacher.getId(), notification);

            if ( isUrgent ) {
                emailService.sendAlertEmail( teacher.getEmail(), notification );
            }
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
