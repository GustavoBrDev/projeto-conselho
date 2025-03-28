package conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS;

import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS.AdvisorResponseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Classe model da entidade Orientador da equipe pedagógica
 * @see User
 * @author Gustavo Stinghen
 * @since 10/03/2025
 *
 * Atualizado em 13/03/2025
 * @author Gustavo Stinghen
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Advisor implements User {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Long register;

    public AdvisorResponseDTO convert() {
        return AdvisorResponseDTO.builder()
                .id(this.id)
                .image(this.image)
                .name(this.name)
                .email(this.email)
                .register(this.register)
                .build();
    }
}
