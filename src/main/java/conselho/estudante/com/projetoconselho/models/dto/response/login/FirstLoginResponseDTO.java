package conselho.estudante.com.projetoconselho.models.dto.response.login;

import conselho.estudante.com.projetoconselho.models.dto.response.users.UserResponseDTO;
import lombok.Builder;

/**
 * DTO para representar a resposta de login (padrão).
 * @author Gustavo Stinghen
 * @since 19/03/2025
 * @see LoginResponse, UserResponseDTO
 */
@Builder
public class FirstLoginResponseDTO implements LoginResponse {
    UserResponseDTO user;
    Boolean isFirstLogin;
    Boolean isAuthenticated;

}
