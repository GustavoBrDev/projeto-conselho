package conselho.estudante.com.projetoconselho.REPOSITORIES.USERS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
