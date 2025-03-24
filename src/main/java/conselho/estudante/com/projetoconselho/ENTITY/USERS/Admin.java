<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/ENTITY/USERS/Admin.java
package conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS;
========
package conselho.estudante.com.projetoconselho.ENTITY.USERS;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/ENTITY/USERS/Admin.java

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
