package conselho.estudante.com.projetoconselho.SERVICES;

import com.postmarkapp.postmark.Postmark;
import com.postmarkapp.postmark.client.ApiClient;
import com.postmarkapp.postmark.client.data.model.message.Message;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Classe de serviço para envio de emails
 * @author Gustavo Stinghen
 * @since 20/03/2025
 * @see EmailService
 */

@AllArgsConstructor
@Service
public class EmailService {

    /**
     * Envia um email de boas vindas para o usuario
     * @param email o email do usuario
     * @param password a senha do usuario
     * @return se o email foi enviado
     */
    public boolean sendWelcomeEmail(String email, String password) {

        ApiClient client = Postmark.getApiClient("0ee11547-2003-4db1-bce4-71a2f9866cac");
        Message message = new Message();
        message.setFrom("conselho-do-estudante@no-reply.com");
        message.setTo(email);
        message.setSubject("Bem-vindo ao Conselho do Estudante");

        String htmlBody = "<html>" +
                "<body>" +
                "<h1>Bem-vindo ao Conselho do Estudante</h1>" +
                "<p>Seja bem-vindo ao Conselho do Estudante! Sua senha de acesso é: " + password + "</p>" +
                "<p>Atenciosamente,<br>Equipe Conselho do Estudante</p>" +
                "</body>" +
                "</html>";
        message.setHtmlBody(htmlBody);
        message.setTextBody("Seja bem-vindo ao Conselho do Estudante! Sua senha de acesso é: " + password);

        try {
            client.deliverMessage(message);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }

    }

    /**
     * Envia um email para o usuario com um token para resetar a senha
     * @param email o email do usuario
     * @param token o token para resetar a senha
     * @return se o email foi enviado
     */
    public boolean sendResetPasswordEmail(String email, String token) {
        ApiClient client = Postmark.getApiClient("0ee11547-2003-4db1-bce4-71a2f9866cac");
        Message message = new Message();
        message.setFrom("conselho-do-estudante@no-reply.com");
        message.setTo(email);
        message.setSubject("Resetar senha");

        String htmlBody = "<html>" +
                "<body>" +
                "<h1>Resetar senha</h1>" +
                "<p>Para resetar sua senha, clique no link abaixo:</p>" +
                "<a href=\"https://conselho-do-estudante.vercel.app/reset-password/" + token + "\">Resetar senha</a>" +
                "<p>Se você não solicitou um reset de senha, ignore esse email.</p>" +
                "<p>Atenciosamente,<br>Equipe Conselho do Estudante</p>" +
                "</body>" +
                "</html>";
        message.setHtmlBody(htmlBody);
        message.setTextBody("O link para resetar sua senha: https://conselho-do-estudante.vercel.app/reset-password/" + token);
        try {
            client.deliverMessage(message);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

}
