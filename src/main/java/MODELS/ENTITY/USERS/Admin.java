package MODELS.ENTITY.USERS;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe model da entidade Admin
 * É uma subclasse de {@link User}
 * É um usuario que tem permissao de administrador
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see User
 */
@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Admin implements User {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

}
