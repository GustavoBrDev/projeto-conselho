package conselho.estudante.com.projetoconselho.SERVICES.CHAT;

import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ChatGroupResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT.*;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.CHAT.TeacherChatGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Classe service da entidade TeacherChatGroup
 * @author Gustavo Stinghen
 * @since 24/03/2025
 * @see TeacherChatGroup
 */

@AllArgsConstructor
@Service
public class TeacherChatGroupService {

    private TeacherChatGroupRepository repository;

    /**
     * Método para criar um chat de estudantes
     * @param name nome do chat
     * @return o chat criado em formato de {@link ChatGroupResponseDTO}
     */
    private ChatGroupResponseDTO create ( String name ) {

        return repository.save(TeacherChatGroup.builder()
                .name(name)
                .build() ).convert();
    }

    /**
     * Metodo para atualizar um chat de estudantes
     * @param id id do chat
     * @param group chat a ser atualizado
     * @return o chat atualizado em formato de {@link ChatGroupResponseDTO}
     */
    private ChatGroupResponseDTO update ( Long id, TeacherChatGroup group ) {

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

        TeacherChatGroup chat = repository.findById(id)
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
    private boolean addChatMessage ( TeacherChatGroup group, ChatMessage message ) {

        switch (message) {
            case TeacherChatMessage teacherChatMessage -> {

                if (group.getMessages().contains(teacherChatMessage)) {
                    return false;
                }

                group.getMessages().add(teacherChatMessage);
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
