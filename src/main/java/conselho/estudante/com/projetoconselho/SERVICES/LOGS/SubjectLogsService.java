package conselho.estudante.com.projetoconselho.SERVICES.LOGS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Subject;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.CourseLogs;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.EditableItem;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.SubjectLogs;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.LOGS.SubjectLogsRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * Classe de serviço para a entidade {@link SubjectLogs}
 * @author Gustavo Stinghen
 * @since 17/03/2025
 * @see SubjectLogs
 */

@AllArgsConstructor
@Service
public class SubjectLogsService {

    private SubjectLogsRepository repository;

    /**
     * Cria um log de um {@link Subject}
     * @param actor o usuario que criou o log
     * @param target o curso alvo
     * @param changes as mudanças efetuadas
     * @param type o tipo de log
     * @return {@link Boolean} se o log foi criado ou nao
     */
    public boolean create(User actor, Subject target, List<EditableItem> changes, String type) {

        try {

            SubjectLogs log = SubjectLogs.builder().
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
     * @return {@link Page} de {@link CourseLogs}
     * @throws NaoEncontradoException se o log nao foi encontrado
     * @see SubjectLogs
     */
    public Page<SubjectLogs> findAll(Pageable pageable) {

        try {
            return repository.findAll(pageable);
        } catch (Exception e) {
            throw new NaoEncontradoException("Log nao encontrado");
        }
    }

    /**
     * Metodo para buscar os logs de uma {@link  Subject}
     * @param actor {@link User} que criou o log
     * @param pageable informacoes de paginacao
     * @return {@link Page} de {@link SubjectLogs}
     * @throws NaoEncontradoException se o log nao foi encontrado
     * @see User, SubjectLogs
     */
    public Page<SubjectLogs> findByActor(User actor, Pageable pageable) {

        try {
            return repository.findByActor(actor, pageable);
        } catch (Exception e) {
            throw new NaoEncontradoException("Log nao encontrado");
        }
    }

    /**
     * Metodo para buscar os logs de um {@link Subject}
     * @param target {@link Subject} alvo do log
     * @param pageable informacoes de paginacao
     * @return {@link Page} de {@link SubjectLogs}
     * @throws NaoEncontradoException se o log nao foi encontrado
     * @see User, SubjectLogs
     */
    public Page<SubjectLogs> findByTarget(Subject target, Pageable pageable) {

        try {
            return repository.findByTarget(target, pageable);
        } catch (Exception e) {
            throw new NaoEncontradoException("Log nao encontrado");
        }
    }

    /**
     * Metodo para buscar os logs de um {@link Subject}
     * @param type {@link String} com o tipo de log
     * @param pageable informacoes de paginacao
     * @return {@link Page} de {@link SubjectLogs}
     * @throws NaoEncontradoException se o log nao foi encontrado
     * @see User, SubjectLogs
     */
    public Page<SubjectLogs> findByType(String type, Pageable pageable) {

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
     * @see SubjectLogs
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
