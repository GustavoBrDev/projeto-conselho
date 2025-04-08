package conselho.estudante.com.projetoconselho.repositories.chat;

import conselho.estudante.com.projetoconselho.models.entity.chat.TeacherChatMessage;
import conselho.estudante.com.projetoconselho.models.entity.users.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherChatMessageRepository extends JpaRepository<TeacherChatMessage, Long> {
    Page<TeacherChatMessage> findByTeacher(Teacher teacher, Pageable pageable);
}
