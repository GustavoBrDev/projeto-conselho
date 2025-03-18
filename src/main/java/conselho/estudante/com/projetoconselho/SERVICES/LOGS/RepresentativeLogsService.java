package conselho.estudante.com.projetoconselho.SERVICES.LOGS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.EditableItem;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.RepresentativeLogs;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.UserLogs;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Representative;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.LOGS.RepresentativeLogsRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * Classe de serviço para a entidade {@link RepresentativeLogs}
 * @author Gustavo Stinghen
 * @since 17/03/2025
 * @see RepresentativeLogs
 */

@Service
@AllArgsConstructor
public class RepresentativeLogsService {

    private RepresentativeLogsRepository repository;

    /**
     * Cria um log de um {@link Representative}
     * @param actor {@link User} que criou o log
     * @param target {@link Representative} alvo do log
     * @param changes {@link List} de {@link EditableItem} que foram feitos ao {@link User} alvo
     * @param type {@link String} com o tipo de log
     * @return {@link Boolean} se o log foi criado ou não
     * @see User, RepresentativeLogs
     */
    public boolean create(User actor, Representative target, List<EditableItem> changes, String type) {

        try {

            RepresentativeLogs log = RepresentativeLogs.builder().
                    actor(actor).
                    target(target).
                    type(type).
                    timestamp(Instant.now()).
                    changes(changes).
                    createdAt( new Date() ).
                    build();

            repository.save(log);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Metodo para buscar todos os logs
     * @param pageable informacoes de paginacao
     * @return {@link Page} de {@link RepresentativeLogs}
     * @throws NaoEncontradoException se o log nao foi encontrado
     * @see RepresentativeLogs
     */
    public Page<RepresentativeLogs> findAll(Pageable pageable) {

        try {
            return repository.findAll(pageable);
        } catch (Exception e) {
            throw new NaoEncontradoException("Log nao encontrado");
        }
    }

    /**
     * Metodo para buscar os logs de um {@link Representative}
     * @param actor {@link User} que criou o log
     * @param pageable informacoes de paginacao
     * @return {@link Page} de {@link RepresentativeLogs}
     * @throws NaoEncontradoException se o log nao foi encontrado
     * @see User, RepresentativeLogs
     */
    public Page<RepresentativeLogs> findByActor(User actor, Pageable pageable) {

        try {
            return repository.findByActor(actor, pageable);
        } catch (Exception e) {
            throw new NaoEncontradoException("Log nao encontrado");
        }
    }

    /**
     * Metodo para buscar os logs de um {@link Representative}
     * @param target {@link Representative} alvo do log
     * @param pageable informacoes de paginacao
     * @return {@link Page} de {@link RepresentativeLogs}
     * @throws NaoEncontradoException se o log nao foi encontrado
     * @see User, RepresentativeLogs
     */
    public Page<UserLogs> findByTarget(Representative target, Pageable pageable) {

        try {
            return repository.findByTarget(target, pageable);
        } catch (Exception e) {
            throw new NaoEncontradoException("Log nao encontrado");
        }
    }

    /**
     * Metodo para buscar os logs de um {@link Representative}
     * @param type {@link String} com o tipo de log
     * @param pageable informacoes de paginacao
     * @return {@link Page} de {@link RepresentativeLogs}
     * @throws NaoEncontradoException se o log nao foi encontrado
     * @see User, RepresentativeLogs
     */
    public Page<RepresentativeLogs> findByType(String type, Pageable pageable) {

        try {
            return repository.findByType(type, pageable);
        } catch (Exception e) {
            throw new NaoEncontradoException("Log nao encontrado");
        }
    }

    /**
     * Metodo para deletar um log
     * @param id {@link String} com o id do log
     * @return {@link Boolean} se o log foi deletado ou nao
     * @throws NaoEncontradoException se o log nao foi encontrado
     * @see RepresentativeLogs
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
