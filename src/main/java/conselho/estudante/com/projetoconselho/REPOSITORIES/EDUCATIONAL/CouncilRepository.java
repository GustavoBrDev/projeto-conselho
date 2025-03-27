package conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.Council;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório para gerenciar operações de persistência relacionadas à entidade {@link Council}.
 *
 * @author joana voigt
 * @since 24/03/2025
 *
 * @see Council
 * @see Classe
 * @see JpaRepository
 */
public interface CouncilRepository extends JpaRepository<Council, Long> {
    public boolean existsByClasse(Classe classe);
}
