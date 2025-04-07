package conselho.estudante.com.projetoconselho.models.dto.response.USERS;

import lombok.Builder;

/**
 * DTO para representar a resposta após operações de criação, leitura ou atualização de uma entidade Technique.
 *
 * @author Joana Voigt
 * @since 17/03/2025
 *
 * Atualizado em 19/03/2025
 * Alterado para uma classe para utilizar abstração (interface)
 * @author Gustavo Stinghen
 */
@Builder
public class TechniqueResponseDTO implements UserResponseDTO {
    Long id;
    String name;
    String password;
    String image;
    String email;
    Long register;
}
