package conselho.estudante.com.projetoconselho.models.dto.response.users;

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
    public Long id;
    public String name;
    public String password;
    public String image;
    public String email;
    public Long register;
}
