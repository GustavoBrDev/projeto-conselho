package conselho.estudante.com.projetoconselho.models.dto.response.LOGIN;

import conselho.estudante.com.projetoconselho.models.dto.response.USERS.UserResponseDTO;
import lombok.Builder;

/**
 * DTO para representar a resposta de login (padrão).
 * @author Gustavo Stinghen
 * @since 19/03/2025
 * @see LoginResponse, UserResponseDTO
 */
@Builder
public class LoginResponseDTO implements LoginResponse {
    UserResponseDTO user;
    Boolean isFirstLogin;
    Boolean isAuthenticated;
}
