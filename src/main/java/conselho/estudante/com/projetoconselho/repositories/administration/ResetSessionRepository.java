package conselho.estudante.com.projetoconselho.repositories.administration;

import conselho.estudante.com.projetoconselho.models.entity.administration.ResetSession;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResetSessionRepository extends MongoRepository<ResetSession, String> {
    void deleteByToken(String token);

    boolean existsByToken(String token);

    ResetSession findByToken(String token);
}
