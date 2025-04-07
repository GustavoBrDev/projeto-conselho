package conselho.estudante.com.projetoconselho.repositories.logs;

import conselho.estudante.com.projetoconselho.models.entity.administration.Shift;
import conselho.estudante.com.projetoconselho.models.entity.logs.ShiftLogs;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShiftLogsRepository extends MongoRepository<ShiftLogs, String> {
    Page<ShiftLogs> findByActor(User actor, Pageable pageable);

    Page<ShiftLogs> findByTarget(Shift target, Pageable pageable);

    Page<ShiftLogs> findByType(String type, Pageable pageable);
}
