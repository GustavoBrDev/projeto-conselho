package conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT;

import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ChatGroupResponseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe model da entidade StudentChatGroup
 * @author Gustavo Stinghen
 * @since 24/03/2025
 * @see ChatGroup
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
public class StudentChatGroup implements ChatGroup{

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    private String name;
    @OneToMany
    private List<StudentChatMessage> messages;
    @OneToMany
    private List<TechniqueChatMessage> responses;
    @OneToMany
    private List<AdvisorChatMessage> advisorResponses;

    /**
     * Metodo para converter um StudentChatGroup para um ChatGroupResponseDTO
     * @return ChatGroupResponseDTO
     */
    public ChatGroupResponseDTO convert() {

        List<ChatMessage> messages = new ArrayList<>();
        messages.addAll(this.messages);
        messages.addAll(this.responses);
        messages.addAll(this.advisorResponses);

        return ChatGroupResponseDTO.builder()
            .name(name)
            .messages(messages)
            .build();
    }
}
