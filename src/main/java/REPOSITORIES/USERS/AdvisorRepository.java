package REPOSITORIES.USERS;

import MODELS.ENTITY.USERS.Advisor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * findByEmail buscar por email
 * findByRegister buscar por matricula
 * findByNameContainingIgnoreCase buscar por nome
 */

public interface AdvisorRepository extends JpaRepository<Advisor, Long> {
    Optional<Advisor> findByEmail(String email);
    Optional<Advisor> findByRegister(Long register);
    Page<Advisor> findByNameContainingIgnoreCase(String name, Pageable pageable);
}