package REPOSITORIES.USERS;

import MODELS.ENTITY.ADMINISTRATION.Classe;
import MODELS.ENTITY.USERS.Supervisor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupervisorRepository extends JpaRepository<Supervisor, Long> {

    public boolean existsByRegister(Long register);

    public boolean existsByEmail(String email);
    public boolean findByEmail(String email);
}
