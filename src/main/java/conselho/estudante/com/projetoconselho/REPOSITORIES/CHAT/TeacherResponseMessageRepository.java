package conselho.estudante.com.projetoconselho.REPOSITORIES.CHAT;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT.TeacherResponseMessage;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherResponseMessageRepository extends JpaRepository<TeacherResponseMessage, Long> {
    Page<TeacherResponseMessage> findByReceiver(Teacher receiver, Pageable pageable);
}
