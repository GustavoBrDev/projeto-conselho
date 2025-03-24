package conselho.estudante.com.projetoconselho.SERVICES.CHAT;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.CHAT.TechniqueChatMessageRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ChatMessageResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT.TechniqueChatMessage;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Technique;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.CHAT.TechniqueChatMessageRepository;
import conselho.estudante.com.projetoconselho.SERVICES.LOGS.ChatMessageLogsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.NoSuchElementException;

/**
 * Classe de serviço para a entidade {@link TechniqueChatMessage}
 * @author Gustavo Stinghen
 * @since 24/03/2025
 * @see TechniqueChatMessage
 */

@AllArgsConstructor
@Service
public class TechniqueChatMessageService {

    private TechniqueChatMessageRepository repository;
    private ChatMessageLogsService logsService;

    /**
     * Método para criar uma mensagem de chat de estudantes
     * @param message a mensagem de chat a ser criada
     * @return a mensagem de chat criada em formato de {@link ChatMessageResponseDTO}
     */
    public ChatMessageResponseDTO create (TechniqueChatMessageRequestDTO message) {

        try {
            logsService.create(message, "create");
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
            return repository.findAll(pageable).map(TechniqueChatMessage::convert);
        } catch (Exception e) {
           throw new NaoEncontradoException("Chat nao encontrado");
        }

    }

    /**
     * Método para buscar todas as mensagens de chat de estudantes de um estudante
     * @param technique estudante que enviou a mensagem
     * @param pageable informacoes de paginacao
     * @return {@link Page} de {@link ChatMessageResponseDTO}
     */
    public Page<ChatMessageResponseDTO> findByTechnique (Technique technique, Pageable pageable) {

        try {
            return repository.findByTechnique(technique, pageable).map(TechniqueChatMessage::convert);
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
                TechniqueChatMessage message = repository.findById(id).get();
                message.setDeletedAt(Instant.now());
                message.setIsDeleted(true);
                return repository.save(message).convert();
            } else {
                throw new NaoEncontradoException("Chat nao encontrado");
            }

        } catch (Exception e) {
           throw new NaoEncontradoException("Chat nao encontrado");
        }

    }
}
