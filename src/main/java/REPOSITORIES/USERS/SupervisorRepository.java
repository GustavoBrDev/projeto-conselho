package REPOSITORIES.USERS;

import MODELS.ENTITY.USERS.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupervisorRepository extends JpaRepository<Supervisor, Long> {

    public boolean existsByRegister(Long register);


    public boolean existsByEmail(String email);
    public Supervisor findByEmail(String email);

}
