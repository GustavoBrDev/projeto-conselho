package REPOSITORIES.LOGS;

import MODELS.ENTITY.CHAT.ChatMessage;
import MODELS.ENTITY.LOGS.ChatMessageLogs;
import MODELS.ENTITY.USERS.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatMessageLogsRepository extends MongoRepository<ChatMessageLogs, String> {
    Page<ChatMessageLogs> findByActor(User actor, Pageable pageable);

    Page<ChatMessageLogs> findByTarget(ChatMessage target, Pageable pageable);

    Page<ChatMessageLogs> findByType(String type, Pageable pageable);
}
