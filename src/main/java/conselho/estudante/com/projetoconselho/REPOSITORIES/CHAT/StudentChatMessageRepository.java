package conselho.estudante.com.projetoconselho.REPOSITORIES.CHAT;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT.StudentChatMessage;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentChatMessageRepository extends JpaRepository<StudentChatMessage, Long> {
    Page<StudentChatMessage> findBySender(Student sender, Pageable pageable);
}
