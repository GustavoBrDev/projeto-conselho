package REPOSITORIES.USERS;

import MODELS.ENTITY.USERS.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
