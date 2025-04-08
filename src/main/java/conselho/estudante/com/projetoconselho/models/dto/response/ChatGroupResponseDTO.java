package conselho.estudante.com.projetoconselho.models.dto.response;

import conselho.estudante.com.projetoconselho.models.entity.chat.ChatMessage;
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
