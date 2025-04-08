package conselho.estudante.com.projetoconselho.repositories.chat;

import conselho.estudante.com.projetoconselho.models.entity.chat.StudentChatMessage;
import conselho.estudante.com.projetoconselho.models.entity.users.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentChatMessageRepository extends JpaRepository<StudentChatMessage, Long> {
    Page<StudentChatMessage> findByStudent(Student student, Pageable pageable);
}
