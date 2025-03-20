package conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS;

import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.StudentResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * Classe model da entidade Aluno
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see User
 *
 * Atualizado em 13/03/2025
 * @author Gustavo Stinghen
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Student implements User {

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
    private Long registration;

    @Column(nullable = false)
    private Boolean isRepresentative;

    @Column(nullable = false)
    private Boolean isHidden;

    @ManyToMany
    private List<Classe> classes;

    /**
     * Método para adicionar uma classe ao aluno
     * @param classe a classe a ser adicionada em formato de {@link Classe}
     * @return um booleano indicando se a classe foi adicionada. Se verdadeiro, a classe foi adicionada ao aluno. Se falso, a classe nao foi adicionada ao aluno
     * A classe nao pode ser adicionada se ela ja estiver na lista de classes
     * @see Classe
     */
    public boolean addClasse(Classe classe) {

        if (this.classes.contains(classe)) {
            return false;
        } else {
            this.classes.add(classe);
            return true;
        }

    }

    /**
     * Metodo para remover uma classe ao aluno
     * @param classe a classe a ser removida em formato de {@link Classe}
     * @return um booleano indicando se a classe foi removida. Se verdadeiro, a classe foi removida ao aluno. Se falso, a classe nao foi removida ao aluno
     * A classe nao pode ser removida se ela nao estiver na lista de classes
     * @see Classe
     */
    public boolean removeClasse(Classe classe) {

        if (this.classes.contains(classe)) {
            this.classes.remove(classe);
            return true;
        } else {
            return false;
        }
    }

    public StudentResponseDTO convert() {
        return StudentResponseDTO.builder()
                .id(id)
                .image(image)
                .name(name)
                .email(email)
                .password(password)
                .isRepresentative(isRepresentative)
                .isHidden(isHidden)
                .build();
    }
}