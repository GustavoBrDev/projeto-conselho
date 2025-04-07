package conselho.estudante.com.projetoconselho.services.administration;

import conselho.estudante.com.projetoconselho.models.entity.administration.ResetSession;
import conselho.estudante.com.projetoconselho.models.entity.users.*;
import conselho.estudante.com.projetoconselho.models.exceptions.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.repositories.administration.ResetSessionRepository;
import conselho.estudante.com.projetoconselho.services.users.AdvisorService;
import conselho.estudante.com.projetoconselho.services.users.StudentService;
import conselho.estudante.com.projetoconselho.services.users.SupervisorService;
import conselho.estudante.com.projetoconselho.services.users.TECHNIQUE.TechniqueService;
import conselho.estudante.com.projetoconselho.services.users.TeacherService;
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
    private SupervisorService supervisorService;
    private TechniqueService techniqueService;
    private StudentService studentService;
    private TeacherService teacherService;
    private AdvisorService advisorService;

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
           User user = resetSession.getUser();

           if ( user instanceof Student) {

               if ( !  studentService.editPassword((Student) user, password) ) {
                   return false;
               }

           } else if ( user instanceof Supervisor){

               if ( !  supervisorService.editPassword((Supervisor) user, password) ) {
                   return false;
               }

           } else if ( user instanceof Technique){

               if ( !  techniqueService.editPassword((Technique) user, password) ) {
                   return false;
               }

           } else if ( user instanceof Teacher) {

               if ( !  teacherService.editPassword((Teacher) user, password) ) {
                   return false;
               }

           } else if ( user instanceof Advisor) {

               if ( !  advisorService.editPassword((Advisor) user, password) ) {
                   return false;
               }
           }
           else {
               throw new NaoEncontradoException("User nao encontrado");
           }

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
