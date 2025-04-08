package conselho.estudante.com.projetoconselho.repositories.educational;

import conselho.estudante.com.projetoconselho.models.entity.educational.TeacherPreCouncil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TeacherPreCouncilRepository extends JpaRepository<TeacherPreCouncil, Long>, JpaSpecificationExecutor<TeacherPreCouncil> {
}
