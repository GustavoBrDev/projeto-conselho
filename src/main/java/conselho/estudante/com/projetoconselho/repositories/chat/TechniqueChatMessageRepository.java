package conselho.estudante.com.projetoconselho.repositories.chat;

import conselho.estudante.com.projetoconselho.models.entity.chat.TechniqueChatMessage;
import conselho.estudante.com.projetoconselho.models.entity.users.Technique;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechniqueChatMessageRepository extends JpaRepository<TechniqueChatMessage, Long> {
    Page<TechniqueChatMessage> findByTechnique(Technique technique, Pageable pageable);
}
