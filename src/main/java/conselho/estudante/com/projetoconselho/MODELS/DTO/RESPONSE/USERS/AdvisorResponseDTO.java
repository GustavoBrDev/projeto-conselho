package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS;


import lombok.Builder;

/**
 * DTO para representar a resposta após operações de criação, leitura ou atualização de uma entidade Advisor.
 */
@Builder
public class AdvisorResponseDTO implements UserResponseDTO {
        Long id;         // Identificador único do orientador
        String name;    // Nome do orientador
        String image;   // Imagem associada ao orientador
        String email;     // Endereço de email do orientador
        Long register;   // Matrícula do orientador,
}
