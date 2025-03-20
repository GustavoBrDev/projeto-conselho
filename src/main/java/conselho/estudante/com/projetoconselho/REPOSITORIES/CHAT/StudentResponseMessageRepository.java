package conselho.estudante.com.projetoconselho.REPOSITORIES.CHAT;

import conselho.estudante.com.projetoconselho.ENTITY.CHAT.StudentResponseMessage;
import conselho.estudante.com.projetoconselho.ENTITY.USERS.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentResponseMessageRepository extends JpaRepository<StudentResponseMessage, Long> {
    Page<StudentResponseMessage> findByReceiver(Student receiver, Pageable pageable);
}
