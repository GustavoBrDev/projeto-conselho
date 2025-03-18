package REPOSITORIES.CHAT;

import MODELS.ENTITY.CHAT.TeacherResponseMessage;
import MODELS.ENTITY.USERS.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherResponseMessageRepository extends JpaRepository<TeacherResponseMessage, Long> {
    Page<TeacherResponseMessage> findByReceiver(Teacher receiver, Pageable pageable);
}
