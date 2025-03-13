package REPOSITORIES.USERS;

import MODELS.ENTITY.ADMINISTRATION.Classe;
import MODELS.ENTITY.USERS.Student;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Classe de repositorio da entidade Student
 * @author Camilly Chelest
 * @since 12/03/2025
 */

public interface StudentRepository extends JpaRepository<Student, Long> {

    public boolean existsByRegistration(Long registration);

    public boolean existsByEmail(String email);

    public boolean findAllByClasses(Classe classe, Pageable pageable);

}
