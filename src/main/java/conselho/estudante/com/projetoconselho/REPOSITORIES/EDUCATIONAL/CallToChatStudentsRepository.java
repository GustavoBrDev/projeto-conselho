package conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.CallToChatStudents;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.Council;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallToChatStudentsRepository extends JpaRepository<CallToChatStudents, Long> {
    CallToChatStudents findByCouncil(Council council);
}
