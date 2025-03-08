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
}
