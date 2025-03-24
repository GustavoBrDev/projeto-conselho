package conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Classe model da entidade Representante
 */

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class Representative {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Student> students;

    @OneToOne
    private Classe representativeOf;
}