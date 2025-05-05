package conselho.estudante.com.projetoconselho.services;

import conselho.estudante.com.projetoconselho.models.entity.administration.Notification;
import conselho.estudante.com.projetoconselho.models.exceptions.EmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Classe de serviço para envio de emails via SMTP (utilizando JavaMailSender)
 *
 * @author Gustavo Stinghen
 * @since 20/03/2025
 * @see EmailService
 */
@AllArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String FROM_EMAIL = "conselhoestudante@gmail.com";

    /**
     * Envia um email de boas-vindas para o usuário.
     * @param email    o email do usuário.
     * @param password a senha do usuário.
     * @return true se o email foi enviado com sucesso, false caso contrário.
     */
    public void sendWelcomeEmail(String email, String password) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(FROM_EMAIL);
            helper.setTo(email);
            helper.setSubject("Bem-vindo ao Conselho do Estudante");

            String htmlBody = "<html>" +
                    "<body>" +
                    "<h1>Bem-vindo ao Conselho do Estudante</h1><b" +
                    "<p>Seja bem-vindo! Sua senha de acesso é: " + password + "</p>" +
                    "<p>Atenciosamente,<br>Equipe Conselho do Estudante</p>" +
                    "</body>" +
                    "</html>";
            helper.setText(htmlBody, true); // true indica que é HTML

            mailSender.send(mimeMessage);
        } catch (Exception e) {
           throw new EmailException("Erro ao enviar email de boas-vindas");
        }
    }

    /**
     * Envia um email para o usuário com um token para resetar a senha.
     *
     * @param email o email do usuário.
     * @param token o token para resetar a senha.
     * @return true se o email foi enviado com sucesso, false caso contrário.
     */
    public void sendResetPasswordEmail(String email, String token) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(FROM_EMAIL);
            helper.setTo(email);
            helper.setSubject("Resetar senha");

            String htmlBody = "<html>" +
                    "<body>" +
                    "<h1>Resetar senha</h1>" +
                    "<p>Para resetar sua senha, clique no link abaixo:</p>" +
                    "<a href=\"https://conselho-do-estudante.vercel.app/reset-password/" + token + "\">Resetar senha</a>" +
                    "<p>Se você não solicitou um reset de senha, ignore esse email.</p>" +
                    "<p>Atenciosamente,<br>Equipe Conselho do Estudante</p>" +
                    "</body>" +
                    "</html>";
            helper.setText(htmlBody, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new EmailException("Erro ao enviar email de reset de senha");
        }
    }

    /**
     * Envia um email de alerta para o usuário.
     *
     * @param email        o email do usuário.
     * @param notification a notificação a ser enviada.
     * @return true se o email foi enviado com sucesso, false caso contrário.
     */
    public void sendAlertEmail(String email, Notification notification) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(FROM_EMAIL);
            message.setTo(email);
            message.setSubject("Atenção!");
            message.setText(notification.getMessage());
            mailSender.send(message);
        } catch (Exception e) {
            throw new EmailException("Erro ao enviar email de alerta");
        }
    }
}
