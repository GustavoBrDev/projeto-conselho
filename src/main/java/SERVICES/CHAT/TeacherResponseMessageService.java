package SERVICES.CHAT;

import MODELS.DTO.RESPONSE.TeacherResponseRequestDTO;
import MODELS.DTO.RESPONSE.ChatResponseDTO;
import MODELS.ENTITY.CHAT.TeacherResponseMessage;
import MODELS.ENTITY.USERS.Teacher;
import REPOSITORIES.CHAT.TeacherResponseMessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.NoSuchElementException;

/**
 * Classe de serviço para a entidade {@link TeacherResponseMessage}
 * @author Gustavo Stinghen
 * @since 17/03/2025
 * @see TeacherResponseMessage
 */

@AllArgsConstructor
@Service
public class TeacherResponseMessageService {

    private TeacherResponseMessageRepository repository;

    /**
     * Método para criar uma mensagem de resposta de chat de professores 
     * @param message a mensagem de chat a ser criada
     * @return a mensagem de chat criada em formato de {@link ChatResponseDTO}
     */
    public ChatResponseDTO create (TeacherResponseRequestDTO message) {

        try {
            return repository.save( message.convert() ).convert();
        } catch (Exception e) {
           throw new NoSuchElementException("Erro ao enviar mensagem");
        }

    }

    /**
     * Método para buscar todas as mensagens de chat de professores
     * @param pageable informacoes de paginacao
     * @return {@link Page} de {@link ChatResponseDTO}
     */
    public Page<ChatResponseDTO> findAll (Pageable pageable) {

        try {
            return repository.findAll(pageable).map(TeacherResponseMessage::convert);
        } catch (Exception e) {
           throw new NaoEncontradoException("Chat nao encontrado");
        }

    }

    /**
     * Método para buscar todas as mensagens de chat enviadas a um professor
     * @param sender professor que recebeu as mensagens
     * @param pageable informacoes de paginacao
     * @return {@link Page} de {@link ChatResponseDTO}
     */
    public Page<ChatResponseDTO> findBySender (Teacher sender, Pageable pageable) {

        try {
            return repository.findByReceiver(sender, pageable).map(TeacherResponseMessage::convert);
        } catch (Exception e) {
           throw new NaoEncontradoException("Chat nao encontrado");
        }

    }

    /**
     * Método para buscar uma mensagem de chat de professores
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
     * Método para deletar uma mensagem de resposta de chat de professores
     * Ele não deleta a mensagem, apenas marca como deletada
     * @param id id da mensagem de chat
     * @return {@link ChatResponseDTO}
     */
    public ChatResponseDTO delete (Long id) {

        try {

            if (repository.existsById(id)) {
                TeacherResponseMessage message = repository.findById(id).get();
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
