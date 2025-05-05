package conselho.estudante.com.projetoconselho.services.chat;

import conselho.estudante.com.projetoconselho.models.dto.request.chat.AdvisorChatMessageRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.ChatMessageResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.chat.AdvisorChatMessage;
import conselho.estudante.com.projetoconselho.models.entity.chat.ChatMessage;
import conselho.estudante.com.projetoconselho.models.entity.users.Advisor;
import conselho.estudante.com.projetoconselho.models.exceptions.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.repositories.chat.AdvisorChatMessageRepository;
import conselho.estudante.com.projetoconselho.services.logs.ChatMessageLogsService;
import conselho.estudante.com.projetoconselho.services.users.AdvisorService;
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
            AdvisorChatMessage converted = message.convert();
            converted = repository.save(converted);
            logsService.create(converted, "create");
            return converted.convert();

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
            Advisor advisor = advisorService.getObjectAdvisor(id);

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
