package conselho.estudante.com.projetoconselho.SERVICES.ADMINISTRATION;

import conselho.estudante.com.projetoconselho.MODEL.ENTITY.ADMINISTRATION.ResetSession;
import conselho.estudante.com.projetoconselho.REPOSITORIES.ADMINISTRATION.ResetSessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Classe de serviço para a entidade {@link ResetSession}
 * Utilizado para resetar a senha do usuário
 * @author Gustavo Stinghen
 * @since 18/03/2025
 * @see ResetSession
 */

@Service
@AllArgsConstructor
public class ResetSessionService {

    private ResetSessionRepository repository;

    /**
     * Cria um token para resetar a senha do usuário
     * @param user {@link User} que deseja resetar a senha
     * @param token {@link String} token para resetar a senha
     * @return {@link Boolean} se o token foi criado ou nao
     */
    public boolean create(User user, String token) {

        try {

            ResetSession resetSession = ResetSession.builder().user(user).token(token).createdAt(new Date()).build();
            repository.save(resetSession);

            return true;
        } catch (Exception e) {
            return false;
        }


    }

    /**
     * Chama o service para resetar a senha do usuário
     * @param token {@link String} token para resetar a senha
     * @param password {@link String} nova senha do usuário
     * @return {@link Boolean} se a senha foi resetada ou nao
     */
    public boolean resetPasswordByToken(String token, String password) {

       try {
           ResetSession resetSession = repository.findByToken(token);
           // Chama o service para editar a senha do usuário
           return this.deleteByToken(token);
       } catch (Exception e) {
           return false;
       }
    }

    /**
     * Verifica se o token existe no banco de dados
     * @param token {@link String} token para resetar a senha
     * @return {@link Boolean} se o token existe ou nao
     */
   public boolean existsByToken(String token) {

        try {
            repository.existsByToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Deleta o token do banco de dados
     * @param token {@link String} token para resetar a senha
     * @return {@link Boolean} se o token foi deletado ou nao
     */
    public boolean deleteByToken(String token) {

        try {
            repository.deleteByToken(token);
            return true;
        } catch (Exception e) {
            throw new NaoEncontradoException("Token nao encontrado");
        }
    }
}
