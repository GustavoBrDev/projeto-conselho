package conselho.estudante.com.projetoconselho.models.dto.response.USERS;


import lombok.Builder;


@Builder
public class TeacherResponseDTO implements UserResponseDTO {
    Long id;
    String name;
    String email;
    String image;
    Long register;
}
