package conselho.estudante.com.projetoconselho.REPOSITORIES.CHAT;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT.AdvisorChatMessage;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT.StudentChatMessage;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Advisor;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvisorChatMessageRepository extends JpaRepository<AdvisorChatMessage, Long> {
    Page<AdvisorChatMessage> findByAdvisor(Advisor advisor, Pageable pageable);
}
