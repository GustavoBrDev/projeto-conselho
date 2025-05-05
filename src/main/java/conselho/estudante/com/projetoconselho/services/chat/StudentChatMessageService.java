package conselho.estudante.com.projetoconselho.services.chat;

import conselho.estudante.com.projetoconselho.models.dto.request.chat.StudentChatMessageRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.ChatMessageResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.chat.StudentChatMessage;
import conselho.estudante.com.projetoconselho.models.entity.users.Student;
import conselho.estudante.com.projetoconselho.models.exceptions.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.repositories.chat.StudentChatMessageRepository;
import conselho.estudante.com.projetoconselho.services.logs.ChatMessageLogsService;
import conselho.estudante.com.projetoconselho.services.users.StudentService;
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
 *
 * Atualizado em 24/03/2025
 * Conexão com o ChatMessageLogsService para gerar logs
 * @author Gustavo Stinghen
 * @see ChatMessageLogsService
 */

@AllArgsConstructor
@Service
public class StudentChatMessageService {

    private StudentChatMessageRepository repository;
    private ChatMessageLogsService logsService;
    private StudentService studentService;

    /**
     * Método para criar uma mensagem de chat de estudantes
     * @param message a mensagem de chat a ser criada
     * @return a mensagem de chat criada em formato de {@link ChatMessageResponseDTO}
     */
    public ChatMessageResponseDTO create (StudentChatMessageRequestDTO message) {

        try {
            StudentChatMessage converted = message.convert();
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
            return repository.findAll(pageable).map(StudentChatMessage::convert);
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
    public Page<ChatMessageResponseDTO> findByStudent (Long id, Pageable pageable) {

        try {
            Student student = studentService.findObjectStudent(id);

            if (student == null) {
                throw new NaoEncontradoException("Estudante nao encontrado");
            }

            return repository.findByStudent(student, pageable).map(StudentChatMessage::convert);
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
                StudentChatMessage message = repository.findById(id).get();
                message.setDeletedAt(Instant.now());
                message.setIsDeleted(true);
                logsService.create(message, "delete");
                return repository.save(message).convert();
            } else {
                throw new NaoEncontradoException("Chat nao encontrado");
            }

        } catch (Exception e) {
           throw new NaoEncontradoException("Chat nao encontrado");
        }

    }
}
