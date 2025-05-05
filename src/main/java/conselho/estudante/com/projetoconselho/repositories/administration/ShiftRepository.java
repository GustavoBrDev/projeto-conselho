package conselho.estudante.com.projetoconselho.repositories.administration;

import conselho.estudante.com.projetoconselho.models.entity.administration.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShiftRepository extends JpaRepository<Shift, Long>, JpaSpecificationExecutor<Shift> {

}
