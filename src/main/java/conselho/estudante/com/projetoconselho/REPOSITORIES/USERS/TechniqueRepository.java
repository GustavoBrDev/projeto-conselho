package conselho.estudante.com.projetoconselho.REPOSITORIES.USERS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Technique;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechniqueRepository extends JpaRepository<Technique, Long> {
}
