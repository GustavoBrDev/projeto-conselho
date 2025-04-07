package conselho.estudante.com.projetoconselho.REPOSITORIES.USERS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Advisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AdvisorRepository extends JpaRepository<Advisor, Long>, JpaSpecificationExecutor<Advisor> {
    boolean existsByEmail(String email);
    boolean existsByRegister(Long register);
    Optional<Advisor> findByEmail(String email);
    Optional<Advisor> findByRegister(Long register);
}
