package conselho.estudante.com.projetoconselho.repositories.administration;

import conselho.estudante.com.projetoconselho.models.entity.administration.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Interface para gerenciamento de operações de acesso a dados relacionadas à entidade `Subject` no banco de dados.
 *
 * @author Joana Voigt
 * @since 17/03/2025
 *
 * @see Subject
 * @see JpaRepository
 * @see JpaSpecificationExecutor
 */
public interface SubjectRepository extends JpaRepository<Subject, Long>, JpaSpecificationExecutor<Subject> {
    /**
     * Verifica se existe uma matéria (Subject) com o nome especificado.
     *
     * @param name Nome da matéria a ser verificada.
     * @return {@code true} se uma matéria com o nome especificado existe,
     * caso contrário {@code false}.
     */
    public boolean existsByName(String name);

}
