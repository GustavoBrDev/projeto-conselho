package conselho.estudante.com.projetoconselho.REPOSITORIES.ADMINISTRATION;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.AvaliableTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliableTeacherRepository extends JpaRepository<AvaliableTeacher, Long> {
}
