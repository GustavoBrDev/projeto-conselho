package conselho.estudante.com.projetoconselho.REPOSITORIES.CHAT;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT.AdvisorChatMessage;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT.TechniqueChatMessage;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Advisor;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Technique;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechniqueChatMessageRepository extends JpaRepository<TechniqueChatMessage, Long> {
    Page<TechniqueChatMessage> findByTechnique(Technique technique, Pageable pageable);
}
