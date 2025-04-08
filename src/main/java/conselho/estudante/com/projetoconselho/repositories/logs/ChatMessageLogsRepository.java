package conselho.estudante.com.projetoconselho.repositories.logs;

import conselho.estudante.com.projetoconselho.models.entity.chat.ChatMessage;
import conselho.estudante.com.projetoconselho.models.entity.logs.ChatMessageLogs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatMessageLogsRepository extends MongoRepository<ChatMessageLogs, String> {
  
    Page<ChatMessageLogs> findByTarget(ChatMessage target, Pageable pageable);

    Page<ChatMessageLogs> findByType(String type, Pageable pageable);
}
