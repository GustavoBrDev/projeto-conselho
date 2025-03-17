package REPOSITORIES.CHAT;

import MODELS.ENTITY.CHAT.StudentChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentChatMessageRepository extends JpaRepository<StudentChatMessage, Long> {
}
