package conselho.estudante.com.projetoconselho.models.dto.response.users;


import lombok.Builder;


@Builder
public class TeacherResponseDTO implements UserResponseDTO {
    public Long id;
    public String name;
    public String email;
    public String image;
    public Long register;
}
