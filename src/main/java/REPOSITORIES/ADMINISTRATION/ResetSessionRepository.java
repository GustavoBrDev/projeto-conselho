package REPOSITORIES.ADMINISTRATION;

import MODELS.ENTITY.ADMINISTRATION.ResetSession;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResetSessionRepository extends MongoRepository<ResetSession, String> {
}
