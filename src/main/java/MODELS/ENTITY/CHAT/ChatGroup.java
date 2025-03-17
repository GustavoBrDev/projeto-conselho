package MODELS.ENTITY.CHAT;

import MODELS.ENTITY.USERS.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Classe model da entidade ChatGroup ( grupo de chat )
 * É um documento do mongodb
 * @author Gustavo Stinghen
 * @since 17/03/2025
 * @see ChatMessage
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class ChatGroup {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id;

    private String name;

    private User firstMember;

    private User secondMember;

    private List<ChatMessage> messages;
}
