<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/ENTITY/USERS/Advisor.java
package conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS;
========
package conselho.estudante.com.projetoconselho.ENTITY.USERS;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/ENTITY/USERS/Advisor.java

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
}
