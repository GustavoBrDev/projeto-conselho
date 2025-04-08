package conselho.estudante.com.projetoconselho.services.chat;

import conselho.estudante.com.projetoconselho.models.dto.response.ChatGroupResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.chat.*;
import conselho.estudante.com.projetoconselho.models.exceptions.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.repositories.chat.StudentChatGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Classe service da entidade StudentChatGroup
 * @author Gustavo Stinghen
 * @since 24/03/2025
 * @see StudentChatGroup
 */

@AllArgsConstructor
@Service
public class StudentChatGroupService {

    private StudentChatGroupRepository repository;

    /**
     * Método para criar um chat de estudantes
     * @param name nome do chat
     * @return o chat criado em formato de {@link ChatGroupResponseDTO}
     */
    private ChatGroupResponseDTO create ( String name ) {

        return repository.save(StudentChatGroup.builder()
                .name(name)
                .build() ).convert();
    }

    /**
     * Metodo para atualizar um chat de estudantes
     * @param id id do chat
     * @param group chat a ser atualizado
     * @return o chat atualizado em formato de {@link ChatGroupResponseDTO}
     */
    private ChatGroupResponseDTO update ( Long id, StudentChatGroup group ) {

       try {

           if ( repository.existsById(id) ) {
               return repository.save(group).convert();
           }

           throw new NaoEncontradoException("Chat nao encontrado");

       } catch (Exception e) {
           throw new NaoEncontradoException("Chat nao encontrado");
       }

    }

    /**
     * Metodo para editar o nome de um chat de estudantes
     * @param id id do chat
     * @param name novo nome do chat
     * @return o chat atualizado em formato de {@link ChatGroupResponseDTO}
     */
    private ChatGroupResponseDTO editName ( Long id, String name ) {

        StudentChatGroup chat = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Chat nao encontrado"));

        chat.setName(name);
        return repository.save(chat).convert();
    }

    /**
     * Metodo para deletar um chat de estudantes
     * @param id id do chat
     */
    private void delete ( Long id ) {

        if ( repository.existsById(id) ) {
            repository.deleteById(id);
        } else {
            throw new NaoEncontradoException("Chat nao encontrado");
        }
    }

    /**
     * Metodo para adicionar uma mensagem a um chat de estudantes
     * Recebe todos os tipos de mensagens
     * @param group chat de estudantes
     * @param message mensagem a ser adicionada
     * @return true se a mensagem foi adicionada, false se ela nao foi adicionada
     */
    private boolean addChatMessage ( StudentChatGroup group, ChatMessage message ) {

        switch (message) {
            case StudentChatMessage studentChatMessage -> {

                if (group.getMessages().contains(studentChatMessage)) {
                    return false;
                }

                group.getMessages().add(studentChatMessage);
                return true;
            }
            case TechniqueChatMessage techniqueChatMessage -> {

                if (group.getResponses().contains(techniqueChatMessage)) {
                    return false;
                }

                group.getResponses().add(techniqueChatMessage);
                return true;
            }
            case AdvisorChatMessage advisorChatMessage -> {

                if (group.getAdvisorResponses().contains(advisorChatMessage)) {
                    return false;
                }

                group.getAdvisorResponses().add(advisorChatMessage);
                return true;
            }
            case null, default -> {
                return false;
            }
        }
    }
}
