package conselho.estudante.com.projetoconselho.repositories.administration;

import conselho.estudante.com.projetoconselho.models.entity.administration.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade {@link Course}.
 *
 * @author joana voigt
 * @since 19/03/2025
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
    /**
     * Verifica se um curso com o nome especificado já existe no banco de dados.
     *
     * @param name Nome do curso a ser verificado.
     * @return {@code true} se um curso com o nome fornecido existir, {@code false} caso contrário.
     */
    public boolean existsByName(String name);

}
