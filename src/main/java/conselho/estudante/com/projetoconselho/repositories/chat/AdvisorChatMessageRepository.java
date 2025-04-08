package conselho.estudante.com.projetoconselho.repositories.chat;

import conselho.estudante.com.projetoconselho.models.entity.chat.AdvisorChatMessage;
import conselho.estudante.com.projetoconselho.models.entity.users.Advisor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvisorChatMessageRepository extends JpaRepository<AdvisorChatMessage, Long> {
    Page<AdvisorChatMessage> findByAdvisor(Advisor advisor, Pageable pageable);
}
