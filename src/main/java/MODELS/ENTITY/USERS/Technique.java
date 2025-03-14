package MODELS.ENTITY.USERS;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Classe model da entidade Técnico da equipe pedagógica
 * @see User
 * @author Gustavo Stinghen
 * @since 10/03/2025
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Technique implements User {

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
}
