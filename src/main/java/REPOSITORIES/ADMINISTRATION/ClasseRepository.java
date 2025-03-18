package REPOSITORIES.ADMINISTRATION;

import MODELS.ENTITY.ADMINISTRATION.Classe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClasseRepository extends JpaRepository<Classe, Long>, JpaSpecificationExecutor<Classe> {
}
