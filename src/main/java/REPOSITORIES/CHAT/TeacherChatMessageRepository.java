package REPOSITORIES.CHAT;

import MODELS.ENTITY.CHAT.TeacherChatMessage;
import MODELS.ENTITY.USERS.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherChatMessageRepository extends JpaRepository<TeacherChatMessage, Long> {
    Page<TeacherChatMessage> findBySender(Teacher sender, Pageable pageable);
}
