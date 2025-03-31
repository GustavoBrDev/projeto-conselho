package conselho.estudante.com.projetoconselho.REPOSITORIES.USERS;


import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Course;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Shift;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Subject;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    boolean existsByRegister(Long register);
    boolean existsByEmail(String email);


    // Para que isso?
    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Teacher t WHERE t.register = :register AND t.id <> :id")
    boolean existsByRegisterAndIdNot(@Param("register") Long register, @Param("id") Long id);


    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Teacher t WHERE t.email = :email AND t.id <> :id")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") Long id);


    Teacher findByEmail(String email);


    // TODO - Alterar isso
    Page<Teacher> findAllByCourses(Course course, Pageable pageable);
    Page<Teacher> findAllBySubjects(Subject subject, Pageable pageable);
    Page<Teacher> findAllByShifts(Shift shift, Pageable pageable);
}
