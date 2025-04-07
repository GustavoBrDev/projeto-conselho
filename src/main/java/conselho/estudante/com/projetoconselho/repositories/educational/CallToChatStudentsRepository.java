package conselho.estudante.com.projetoconselho.repositories.educational;

import conselho.estudante.com.projetoconselho.models.entity.educational.CallToChatStudents;
import conselho.estudante.com.projetoconselho.models.entity.educational.Council;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallToChatStudentsRepository extends JpaRepository<CallToChatStudents, Long> {
    CallToChatStudents findByCouncil(Council council);
}
