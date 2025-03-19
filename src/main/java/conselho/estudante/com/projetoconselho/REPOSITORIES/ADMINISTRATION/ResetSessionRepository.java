package conselho.estudante.com.projetoconselho.REPOSITORIES.ADMINISTRATION;

import conselho.estudante.com.projetoconselho.MODEL.ENTITY.ADMINISTRATION.ResetSession;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResetSessionRepository extends MongoRepository<ResetSession, String> {
    void deleteByToken(String token);

    boolean existsByToken(String token);

    ResetSession findByToken(String token);
}
