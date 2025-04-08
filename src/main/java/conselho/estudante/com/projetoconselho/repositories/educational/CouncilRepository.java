package conselho.estudante.com.projetoconselho.repositories.educational;

import conselho.estudante.com.projetoconselho.models.entity.administration.Classe;
import conselho.estudante.com.projetoconselho.models.entity.educational.Council;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

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

    @Query("SELECT c FROM Council c " +
            "WHERE (c.teacherPreCouncilFinished = false AND c.teacherPreCouncilEndDate <= :currentDate) " +
            "OR (c.representativePreCouncilFinished = false AND c.representativePreCouncilEndDate <= :currentDate)")
    List<Council> findOpenCouncils(@Param("currentDate") Date currentDate);


}
