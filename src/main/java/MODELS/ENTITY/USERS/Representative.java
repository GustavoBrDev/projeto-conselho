package MODELS.ENTITY.USERS;

import MODELS.ENTITY.ADMINISTRATION.Classe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Representative {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany
    private List<Student> students;

    @OneToOne
    private Classe representativeOf;

}
