package MODELS.ENTITY.USERS;

import MODELS.ENTITY.ADMINISTRATION.Classe;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Classe model da entidade Aluno
 * É uma subclasse de {@link RegularUser}
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see RegularUser
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode (callSuper = true) //Pedir isso ao professor
@Data
@Entity
public class Student extends RegularUser {

    @Column(nullable = false)
    private Long registration;
    @Column(nullable = false)
    private boolean isRepresentative;
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
}
