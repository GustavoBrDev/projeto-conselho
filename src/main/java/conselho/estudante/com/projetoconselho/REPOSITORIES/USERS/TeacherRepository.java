package conselho.estudante.com.projetoconselho.REPOSITORIES.USERS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Classe de repositorio da entidade Teacher
 * @author Alex Zastrow
 */

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    /*
     * Metodo para encontrar um professor por email
     * @param email
     * @return
     */
    Optional<Teacher> findByEmail(String email);

    /*
     * Metodo para encontrar um professor por username
     * @param username
     * @return
     */
    Optional<Teacher> findByUsername(String username);

    /*
     * Filtros para listar professores por curso (usando IDs)
     * @param courseId
     * @param pageable
     * @return
     */
    Page<Teacher> findByCoursesId(Long courseId, Pageable pageable);

    /*
     * Filtros para listar professores por disciplina (usando IDs)
     * @param subjectId
     * @param pageable
     * @return
     */
    Page<Teacher> findBySubjectsId(Long subjectId, Pageable pageable);

    /*
     * Filtros para listar professores por turno (usando IDs)
     * @param shiftId
     * @param pageable
     * @return
     */
    Page<Teacher> findByShiftsId(Long shiftId, Pageable pageable);

    /*
     * Verificar se existe um professor com determinado email (para validação)
     * @param email
     * @return
     */
    boolean existsByEmail(String email);

    /*
     * Verificar se existe um professor com determinado username (para validação)
     * @param username
     * @return
     */
    boolean existsByUsername(String username);
}
