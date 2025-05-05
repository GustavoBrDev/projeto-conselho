package conselho.estudante.com.projetoconselho.repositories.administration;

import conselho.estudante.com.projetoconselho.models.entity.educational.AvaliableTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliableTeacherRepository extends JpaRepository<AvaliableTeacher, Long> {
}
