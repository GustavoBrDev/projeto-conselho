package conselho.estudante.com.projetoconselho.repositories.educational;

import conselho.estudante.com.projetoconselho.models.entity.educational.Council;
import conselho.estudante.com.projetoconselho.models.entity.educational.ViewedStudents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewedStudentsRepository extends JpaRepository<ViewedStudents, Long> {

        ViewedStudents findByCouncil(Council council);
}
