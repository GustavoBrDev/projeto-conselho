package conselho.estudante.com.projetoconselho.REPOSITORIES.LOGS;

import conselho.estudante.com.projetoconselho.ENTITY.CHAT.ChatMessage;
import conselho.estudante.com.projetoconselho.ENTITY.LOGS.ChatMessageLogs;
import conselho.estudante.com.projetoconselho.ENTITY.USERS.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatMessageLogsRepository extends MongoRepository<ChatMessageLogs, String> {
    Page<ChatMessageLogs> findByActor(User actor, Pageable pageable);

    Page<ChatMessageLogs> findByTarget(ChatMessage target, Pageable pageable);

    Page<ChatMessageLogs> findByType(String type, Pageable pageable);
}
