package conselho.estudante.com.projetoconselho.SERVICES.CHAT;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.CHAT.AdvisorChatMessageRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ChatMessageResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT.AdvisorChatMessage;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Advisor;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.CHAT.AdvisorChatMessageRepository;
import conselho.estudante.com.projetoconselho.SERVICES.LOGS.ChatMessageLogsService;
import conselho.estudante.com.projetoconselho.SERVICES.USERS.AdvisorService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.NoSuchElementException;

/**
 * Classe de serviço para a entidade {@link AdvisorChatMessage}
 * @author Gustavo Stinghen
 * @since 24/03/2025
 * @see AdvisorChatMessage
 */

@AllArgsConstructor
@Service
public class AdvisorChatMessageService {

    private AdvisorChatMessageRepository repository;
    private ChatMessageLogsService logsService;
    private AdvisorService advisorService;

    /**
     * Método para criar uma mensagem de chat de estudantes
     * @param message a mensagem de chat a ser criada
     * @return a mensagem de chat criada em formato de {@link ChatMessageResponseDTO}
     */
    public ChatMessageResponseDTO create (AdvisorChatMessageRequestDTO message) {

        try {
            logsService.create( message, "create" );
            return repository.save(message.convert()).convert();
        } catch (Exception e) {
           throw new NoSuchElementException("Erro ao enviar mensagem");
        }

    }

    /**
     * Método para buscar todas as mensagens de chat de estudantes
     * @param pageable informacoes de paginacao
     * @return {@link Page} de {@link ChatMessageResponseDTO}
     */
    public Page<ChatMessageResponseDTO> findAll (Pageable pageable) {

        try {
            return repository.findAll(pageable).map(AdvisorChatMessage::convert);
        } catch (Exception e) {
           throw new NaoEncontradoException("Chat nao encontrado");
        }

    }

    /**
     * Método para buscar todas as mensagens de chat de estudantes de um estudante
     * @param id id do estudante
     * @param pageable informacoes de paginacao
     * @return {@link Page} de {@link ChatMessageResponseDTO}
     */
    public Page<ChatMessageResponseDTO> findByAdvisor (Long id, Pageable pageable) {

        try {
            Advisor advisor = advisorService.getAdvisorById(id);

            if (advisor == null) {
                throw new NaoEncontradoException("Orientador nao encontrado");
            }

            return repository.findByAdvisor(advisor, pageable).map(AdvisorChatMessage::convert);
        } catch (Exception e) {
           throw new NaoEncontradoException("Chat nao encontrado");
        }

    }

    /**
     * Método para buscar uma mensagem de chat de estudantes
     * @param id id da mensagem de chat
     * @return {@link ChatMessageResponseDTO}
     */
    public ChatMessageResponseDTO findById (Long id) {

        try {
            return repository.findById(id).get().convert();
        } catch (Exception e) {
           throw new NaoEncontradoException("Chat nao encontrado");
        }

    }

    /**
     * Método para deletar uma mensagem de chat de estudantes
     * Ele não deleta a mensagem, apenas marca como deletada
     * @param id id da mensagem de chat
     * @return {@link ChatMessageResponseDTO}
     */
    public ChatMessageResponseDTO delete (Long id) {

        try {

            if (repository.existsById(id)) {
                AdvisorChatMessage message = repository.findById(id).get();
                message.setDeletedAt(Instant.now());
                message.setIsDeleted(true);
                logsService.create( message, "delete" );
                return repository.save(message).convert();
            } else {
                throw new NaoEncontradoException("Chat nao encontrado");
            }

        } catch (Exception e) {
           throw new NaoEncontradoException("Chat nao encontrado");
        }

    }
}
