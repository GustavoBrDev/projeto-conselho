package REPOSITORIES.ADMINISTRATION;

import MODELS.ENTITY.ADMINISTRATION.Classe;
import MODELS.ENTITY.ADMINISTRATION.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.awt.print.Pageable;

/**
 * Repositório de dados para gerenciamento da entidade {@link Classe}.
 *
 * @author Joana Voigt
 * @since 18/03/2025
 *
 * @see Classe
 * @see Course
 * @see JpaRepository
 * @see JpaSpecificationExecutor
 */
public interface ClasseRepository extends JpaRepository<Classe, Long>, JpaSpecificationExecutor<Classe> {

    public boolean existsByName(String name);
    public boolean existsByAcronym(String acronym);
    public boolean findAllByCourse(Course course, Pageable pageable);
}
