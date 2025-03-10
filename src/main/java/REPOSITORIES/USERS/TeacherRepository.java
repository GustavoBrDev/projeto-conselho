package REPOSITORIES.USERS;

import MODELS.ENTITY.USERS.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
