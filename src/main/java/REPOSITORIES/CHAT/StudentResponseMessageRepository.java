package REPOSITORIES.CHAT;

import MODELS.ENTITY.CHAT.StudentResponseMessage;
import MODELS.ENTITY.USERS.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentResponseMessageRepository extends JpaRepository<StudentResponseMessage, Long> {
    Page<StudentResponseMessage> findByReceiver(Student receiver, Pageable pageable);
}
