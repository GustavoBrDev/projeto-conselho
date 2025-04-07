package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS;

import lombok.Builder;

@Builder
public class AdvisorResponseDTO {
    private Long id;
    private String image;
    private String name;
    private String email;
    private Long register;
}
