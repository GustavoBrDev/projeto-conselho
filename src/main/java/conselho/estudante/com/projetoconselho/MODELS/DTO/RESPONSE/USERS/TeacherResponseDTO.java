package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS;


import lombok.Builder;


@Builder
public class TeacherResponseDTO implements UserResponseDTO {
    Long id;
    String name;
    String email;
    String image;
    Long register;
}
