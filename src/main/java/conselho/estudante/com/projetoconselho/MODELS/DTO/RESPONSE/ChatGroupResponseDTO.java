package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ChatGroupResponseDTO {

    private String name;

    private List<ChatMessage> messages;
}
