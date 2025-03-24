package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS;

import lombok.Builder;

/**
 * Classe de DTO da entidade Student, para ser usada nas respostas da API
 * @author Camilly Chelest
 * @since 12/03/2025
 *
 * Atualizado em 17/03/2025
 * Adicionado atributos isRepresentative e isHidden
 * @author Gustavo Stinghen
 *
 * Atualizado em 19/03/2025
 * Alterado para uma classe para utilizar abstração (interface)
 * @author Gustavo Stinghen
 */

@Builder
public class StudentResponseDTO implements UserResponseDTO {
    Long id;
    String name;
    String email;
    String password;
    String image;
    Boolean isRepresentative;
    Boolean isHidden;
}
