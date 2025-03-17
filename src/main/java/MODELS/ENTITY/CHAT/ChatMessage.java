package MODELS.ENTITY.CHAT;

import MODELS.ENTITY.USERS.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Classe model da entidade ChatMessage ( mensagem de chat )
 * @author Gustavo Stinghen
 * @since 17/03/2025
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatMessage {

    private String text;

    private User sender;

    private Instant timestamp;

    private Boolean isRead;

    private Boolean isDeleted;

    private Instant deletedAt;

}
