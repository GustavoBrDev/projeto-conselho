package conselho.estudante.com.projetoconselho.repositories.chat;

import conselho.estudante.com.projetoconselho.models.entity.chat.StudentChatGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentChatGroupRepository extends JpaRepository<StudentChatGroup, Long> {
}
