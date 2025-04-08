package conselho.estudante.com.projetoconselho.repositories.users;

import conselho.estudante.com.projetoconselho.models.entity.administration.Classe;
import conselho.estudante.com.projetoconselho.models.entity.users.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Classe de repositorio da entidade Student
 * @author Camilly Chelest
 * @since 12/03/2025
 */

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    public boolean existsByRegistration(Long registration);

    public boolean existsByEmail(String email);

    public Page<Student> findAllByClasses(Classe classe, Pageable pageable);

    public Student findByEmail(String email);
}
