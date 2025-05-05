package conselho.estudante.com.projetoconselho.services.logs;

import conselho.estudante.com.projetoconselho.models.entity.logs.LoginLogs;
import conselho.estudante.com.projetoconselho.models.entity.logs.UserLogs;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import conselho.estudante.com.projetoconselho.models.exceptions.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.repositories.logs.LoginLogsRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * Classe de serviço para a entidade {@link LoginLogs}
 * @author Gustavo Stinghen
 * @since 19/03/2025
 * @see LoginLogs
 */

@Service
@AllArgsConstructor
public class LoginLogsService {

    private LoginLogsRepository repository;

    /**
     * Cria um log de um {@link User}
     * @param user {@link User} o usuario que realizou o login
     * @return {@link Boolean} se o log foi criado ou não
     * @see User, LoginLogs
     */
    public boolean create(User user) {

        try {

            LoginLogs log = LoginLogs.builder().
                    user(user).
                    timestamp(Instant.now()).
                    build();

            repository.save(log);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Page<LoginLogs> findAll(Pageable pageable) {

        try {
            return repository.findAll(pageable);
        } catch (Exception e) {
            throw new NaoEncontradoException("Log nao encontrado");
        }
    }

    /**
     * Metodo para buscar os logs de um {@link User}
     * @param user {@link User} o usuario que realizou o login
     * @param pageable informacoes de paginacao
     * @return {@link Page} de {@link UserLogs}
     * @throws NaoEncontradoException se o log nao foi encontrado
     * @see User, LoginLogs
     */
    public Page<LoginLogs> findByUser(User user, Pageable pageable) {

        try {
            return repository.findByUser(user, pageable);
        } catch (Exception e) {
            throw new NaoEncontradoException("Log nao encontrado");
        }
    }

    /**
     * Metodo para buscar os logs de um {@link User}
     * @param user {@link User} o usuario que realizou o login
     * @return {@link Page} de {@link UserLogs}
     * @throws NaoEncontradoException se o log nao foi encontrado
     * @see User, UserLogs
     */
    public boolean verifyFirstLogin (User user ) {

        try {
            return repository.existsByUser(user);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Metodo para deletar um log
     * @param id {@link String} com o id do log
     * @return {@link Boolean} se o log foi deletado ou nao
     * @throws NaoEncontradoException se o log nao foi encontrado
     * @see UserLogs
     */
    public boolean delete(String id) {
        try {

            if (repository.existsById(id)) {
                repository.deleteById(id);
                return true;
            } else {
                throw new NaoEncontradoException("Log nao encontrado");
            }

        } catch (Exception e) {
            return false;
        }
    }
}
