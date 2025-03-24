package conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.RepresentativePreCouncil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RepresentativePreCouncilRepository extends
        JpaRepository<RepresentativePreCouncil, Long>,
        JpaSpecificationExecutor<RepresentativePreCouncil> {
}