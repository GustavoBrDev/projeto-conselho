package conselho.estudante.com.projetoconselho.SERVICES.LOGS;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Shift;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.EditableItem;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.ShiftLogs;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.LOGS.ShiftLogsRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * Classe de serviço para a entidade {@link Shift}
 * @author Gustavo Stinghen
 * @since 17/03/2025
 * @see ShiftLogs
 */

@AllArgsConstructor
@Service
public class ShiftLogsService {

    private ShiftLogsRepository repository;

    /**
     * Cria um log de um {@link Shift}
     * @param actor o usuario que criou o log
     * @param target o turno alvo
     * @param changes as mudanças efetuadas
     * @param type o tipo de log
     * @return {@link Boolean} se o log foi criado ou nao
     */
    public boolean create(User actor, Shift target, List<EditableItem> changes, String type) {

        try {

            ShiftLogs log = ShiftLogs.builder().
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
     * @return {@link Page} de {@link ShiftLogs}
     * @throws NaoEncontradoException se o log nao foi encontrado
     * @see ShiftLogs
     */
    public Page<ShiftLogs> findAll(Pageable pageable) {

        try {
            return repository.findAll(pageable);
        } catch (Exception e) {
            throw new NaoEncontradoException("Log nao encontrado");
        }
    }

    /**
     * Metodo para buscar os logs de um {@link  Shift}
     * @param actor {@link User} que criou o log
     * @param pageable informacoes de paginacao
     * @return {@link Page} de {@link ShiftLogs}
     * @throws NaoEncontradoException se o log nao foi encontrado
     * @see User, ShiftLogs
     */
    public Page<ShiftLogs> findByActor(User actor, Pageable pageable) {

        try {
            return repository.findByActor(actor, pageable);
        } catch (Exception e) {
            throw new NaoEncontradoException("Log nao encontrado");
        }
    }

    /**
     * Metodo para buscar os logs de um {@link Shift}
     * @param target {@link Shift} alvo do log
     * @param pageable informacoes de paginacao
     * @return {@link Page} de {@link ShiftLogs}
     * @throws NaoEncontradoException se o log nao foi encontrado
     * @see User, ShiftLogs
     */
    public Page<ShiftLogs> findByTarget(Shift target, Pageable pageable) {

        try {
            return repository.findByTarget(target, pageable);
        } catch (Exception e) {
            throw new NaoEncontradoException("Log nao encontrado");
        }
    }

    /**
     * Metodo para buscar os logs de um {@link Shift}
     * @param type {@link String} com o tipo de log
     * @param pageable informacoes de paginacao
     * @return {@link Page} de {@link ShiftLogs}
     * @throws NaoEncontradoException se o log nao foi encontrado
     * @see User, ShiftLogs
     */
    public Page<ShiftLogs> findByType(String type, Pageable pageable) {

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
     * @see ShiftLogs
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
