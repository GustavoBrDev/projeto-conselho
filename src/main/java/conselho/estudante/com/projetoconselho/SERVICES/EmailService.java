package conselho.estudante.com.projetoconselho.SERVICES;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmailService {

    public boolean sendResetPasswordEmail(String email, String token) {
        return false;
    }
}
