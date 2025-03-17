package SERVICES.CHAT;

import REPOSITORIES.CHAT.StudentChatMessageRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class StudentChatMessageService {

    private StudentChatMessageRepository repository;


}
