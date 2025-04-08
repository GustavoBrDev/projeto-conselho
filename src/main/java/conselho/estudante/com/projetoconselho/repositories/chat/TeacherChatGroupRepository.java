package conselho.estudante.com.projetoconselho.repositories.chat;

import conselho.estudante.com.projetoconselho.models.entity.chat.TeacherChatGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherChatGroupRepository extends JpaRepository<TeacherChatGroup, Long> {
}
