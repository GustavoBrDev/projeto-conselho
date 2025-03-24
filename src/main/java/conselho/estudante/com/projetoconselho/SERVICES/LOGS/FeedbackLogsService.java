package conselho.estudante.com.projetoconselho.SERVICES.LOGS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.Council;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.Feedback;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.EditableItem;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.FeedbackLogs;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.LOGS.FeedbackLogsRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * Classe de serviço para a entidade {@link FeedbackLogs}
 * @author Gustavo Stinghen
 * @since 17/03/2025
 * @see FeedbackLogs
 *
 * Atualizado em 19/03/2025
 * Adicionado o metodo de criar um log sem mudanças
 * @author Gustavo Stinghen
 */

@AllArgsConstructor
@Service
public class FeedbackLogsService {

    private FeedbackLogsRepository repository;

    /**
     * Cria um log de um {@link Council}
     * @param actor a entidade que criou o log
     * @param target o conselho alvo
     * @param changes as mudanças efetuadas
     * @param type o tipo de log
     * @return {@link Boolean} se o log foi criado ou nao
     */
    public boolean create(Object actor, Feedback target, List<EditableItem> changes, String type) {

        try {

            FeedbackLogs log = FeedbackLogs.builder().
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
     * Cria um log de um {@link Feedback}
     * @param actor a entidade que criou o log
     * @param target o feedback alvo
     * @param type o tipo de log
     * @return {@link Boolean} se o log foi criado ou nao
     */
    public boolean create(Object actor, Feedback target, String type) {

        try {

            FeedbackLogs log = FeedbackLogs.builder().
                    actor(actor).
                    target(target).
                    type(type).
                    timestamp(Instant.now()).
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
     * @return {@link Page} de {@link FeedbackLogs}
     * @throws NaoEncontradoException se o log nao foi encontrado
     * @see FeedbackLogs
     */
    public Page<FeedbackLogs> findAll(Pageable pageable) {

        try {
            return repository.findAll(pageable);
        } catch (Exception e) {
            throw new NaoEncontradoException("Log nao encontrado");
        }
    }

    /**
     * Metodo para buscar os logs de um {@link  Feedback}
     * @param actor {@link Object} que criou o log
     * @param pageable informacoes de paginacao
     * @return {@link Page} de {@link FeedbackLogs}
     * @throws NaoEncontradoException se o log nao foi encontrado
     * @see User, FeedbackLogs
     */
    public Page<FeedbackLogs> findByActor(Object actor, Pageable pageable) {

        try {
            return repository.findByActor(actor, pageable);
        } catch (Exception e) {
            throw new NaoEncontradoException("Log nao encontrado");
        }
    }

    /**
     * Metodo para buscar os logs de um {@link Feedback}
     * @param target {@link Feedback } alvo do log
     * @param pageable informacoes de paginacao
     * @return {@link Page} de {@link FeedbackLogs}
     * @throws NaoEncontradoException se o log nao foi encontrado
     * @see User, FeedbackLogs
     */
    public Page<FeedbackLogs> findByTarget(Feedback target, Pageable pageable) {

        try {
            return repository.findByTarget(target, pageable);
        } catch (Exception e) {
            throw new NaoEncontradoException("Log nao encontrado");
        }
    }

    /**
     * Metodo para buscar os logs de um {@link Feedback}
     * @param type {@link String} com o tipo de log
     * @param pageable informacoes de paginacao
     * @return {@link Page} de {@link FeedbackLogs}
     * @throws NaoEncontradoException se o log nao foi encontrado
     * @see User, FeedbackLogs
     */
    public Page<FeedbackLogs> findByType(String type, Pageable pageable) {

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
     * @see FeedbackLogs
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
