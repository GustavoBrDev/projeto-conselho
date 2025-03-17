package REPOSITORIES.CHAT;

import MODELS.ENTITY.CHAT.StudentResponseMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentResponseMessageRepository extends JpaRepository<StudentResponseMessage, Long> {
}
