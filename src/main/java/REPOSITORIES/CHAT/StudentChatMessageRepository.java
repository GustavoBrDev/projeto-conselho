package REPOSITORIES.CHAT;

import MODELS.ENTITY.CHAT.StudentChatMessage;
import MODELS.ENTITY.USERS.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentChatMessageRepository extends JpaRepository<StudentChatMessage, Long> {
    Page<StudentChatMessage> findBySender(Student sender, Pageable pageable);
}
