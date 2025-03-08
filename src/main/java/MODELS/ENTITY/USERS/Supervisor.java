package MODELS.ENTITY.USERS;

import MODELS.ENTITY.ADMINISTRATION.Course;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true) //Pedir isso ao professor
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class Supervisor extends Staff {

    @OneToMany
    private List<Course> courses;
}
