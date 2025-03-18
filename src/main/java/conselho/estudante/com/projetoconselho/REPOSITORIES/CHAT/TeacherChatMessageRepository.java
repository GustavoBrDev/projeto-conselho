package conselho.estudante.com.projetoconselho.REPOSITORIES.CHAT;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT.TeacherChatMessage;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherChatMessageRepository extends JpaRepository<TeacherChatMessage, Long> {
    Page<TeacherChatMessage> findBySender(Teacher sender, Pageable pageable);
}
