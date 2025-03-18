package conselho.estudante.com.projetoconselho.SERVICES.CHAT;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.CHAT.StudentChatMessageRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ChatResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT.StudentChatMessage;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Student;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.CHAT.StudentChatMessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.NoSuchElementException;

/**
 * Classe de serviço para a entidade {@link StudentChatMessage}
 * @author Gustavo Stinghen
 * @since 17/03/2025
 * @see StudentChatMessage
 */

@AllArgsConstructor
@Service
public class StudentChatMessageService {

    private StudentChatMessageRepository repository;

    /**
     * Método para criar uma mensagem de chat de estudantes
     * @param message a mensagem de chat a ser criada
     * @return a mensagem de chat criada em formato de {@link ChatResponseDTO}
     */
    public ChatResponseDTO create (StudentChatMessageRequestDTO message) {

        try {
            return repository.save(message.convert()).convert();
        } catch (Exception e) {
           throw new NoSuchElementException("Erro ao enviar mensagem");
        }

    }

    /**
     * Método para buscar todas as mensagens de chat de estudantes
     * @param pageable informacoes de paginacao
     * @return {@link Page} de {@link ChatResponseDTO}
     */
    public Page<ChatResponseDTO> findAll (Pageable pageable) {

        try {
            return repository.findAll(pageable).map(StudentChatMessage::convert);
        } catch (Exception e) {
           throw new NaoEncontradoException("Chat nao encontrado");
        }

    }

    /**
     * Método para buscar todas as mensagens de chat de estudantes de um estudante
     * @param sender estudante que enviou a mensagem
     * @param pageable informacoes de paginacao
     * @return {@link Page} de {@link ChatResponseDTO}
     */
    public Page<ChatResponseDTO> findBySender (Student sender, Pageable pageable) {

        try {
            return repository.findBySender(sender, pageable).map(StudentChatMessage::convert);
        } catch (Exception e) {
           throw new NaoEncontradoException("Chat nao encontrado");
        }

    }

    /**
     * Método para buscar uma mensagem de chat de estudantes
     * @param id id da mensagem de chat
     * @return {@link ChatResponseDTO}
     */
    public ChatResponseDTO findById (Long id) {

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
     * @return {@link ChatResponseDTO}
     */
    public ChatResponseDTO delete (Long id) {

        try {

            if (repository.existsById(id)) {
                StudentChatMessage message = repository.findById(id).get();
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
