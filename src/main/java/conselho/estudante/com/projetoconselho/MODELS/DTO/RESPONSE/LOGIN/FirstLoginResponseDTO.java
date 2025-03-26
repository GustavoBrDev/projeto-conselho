package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.LOGIN;

import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS.UserResponseDTO;
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
