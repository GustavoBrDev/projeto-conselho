package MODELS.ENTITY.USERS;

import MODELS.ENTITY.ADMINISTRATION.Classe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Classe model da entidade Representante
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see Student
 */
@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Representative {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Student> students;

    @OneToOne
    private Classe representativeOf;

}
