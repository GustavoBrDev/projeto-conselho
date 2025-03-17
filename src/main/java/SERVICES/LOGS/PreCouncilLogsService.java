package SERVICES.LOGS;

import MODELS.ENTITY.EDUCATIONAL.PreCouncil;
import MODELS.ENTITY.LOGS.EditableItem;
import MODELS.ENTITY.LOGS.PreCouncilLogs;
import MODELS.ENTITY.USERS.User;
import MODELS.EXCEPTIONS.NaoEncontradoException;
import REPOSITORIES.LOGS.PreCouncilLogsRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * Classe de serviço para a entidade {@link PreCouncilLogs}
 * @author Gustavo Stinghen
 * @since 17/03/2025
 * @see PreCouncilLogs
 */

@AllArgsConstructor
@Service
public class PreCouncilLogsService {

    private PreCouncilLogsRepository repository;

    /**
     * Cria um log de um {@link PreCouncil}
     * @param actor o usuario que criou o log
     * @param target o pré-conselho alvo
     * @param changes as mudanças efetuadas
     * @param type o tipo de log
     * @return {@link Boolean} se o log foi criado ou nao
     */
    public boolean create(User actor, PreCouncil target, List<EditableItem> changes, String type) {

        try {

            PreCouncilLogs log = PreCouncilLogs.builder().
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
     * @return {@link Page} de {@link PreCouncilLogs}
     * @throws NaoEncontradoException se o log nao foi encontrado
     * @see PreCouncilLogs
     */
    public Page<PreCouncilLogs> findAll(Pageable pageable) {

        try {
            return repository.findAll(pageable);
        } catch (Exception e) {
            throw new NaoEncontradoException("Log nao encontrado");
        }
    }

    /**
     * Metodo para buscar os logs de um {@link  PreCouncil}
     * @param actor {@link User} que criou o log
     * @param pageable informacoes de paginacao
     * @return {@link Page} de {@link PreCouncilLogs}
     * @throws NaoEncontradoException se o log nao foi encontrado
     * @see User, PreCouncilLogs
     */
    public Page<PreCouncilLogs> findByActor(User actor, Pageable pageable) {

        try {
            return repository.findByActor(actor, pageable);
        } catch (Exception e) {
            throw new NaoEncontradoException("Log nao encontrado");
        }
    }

    /**
     * Metodo para buscar os logs de um {@link PreCouncil}
     * @param target {@link PreCouncil } alvo do log
     * @param pageable informacoes de paginacao
     * @return {@link Page} de {@link PreCouncilLogs}
     * @throws NaoEncontradoException se o log nao foi encontrado
     * @see User, PreCouncilLogs
     */
    public Page<PreCouncilLogs> findByTarget(PreCouncil target, Pageable pageable) {

        try {
            return repository.findByTarget(target, pageable);
        } catch (Exception e) {
            throw new NaoEncontradoException("Log nao encontrado");
        }
    }

    /**
     * Metodo para buscar os logs de um {@link PreCouncil}
     * @param type {@link String} com o tipo de log
     * @param pageable informacoes de paginacao
     * @return {@link Page} de {@link PreCouncilLogs}
     * @throws NaoEncontradoException se o log nao foi encontrado
     * @see User, PreCouncilLogs
     */
    public Page<PreCouncilLogs> findByType(String type, Pageable pageable) {

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
     * @see PreCouncilLogs
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
