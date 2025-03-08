package MODELS.ENTITY.EDUCATIONAL;

import MODELS.ENTITY.ADMINISTRATION.Subject;
import MODELS.ENTITY.USERS.Student;
import MODELS.ENTITY.USERS.Teacher;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class TeacherPreCouncil extends PreCouncil {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Subject subject;

    @ManyToMany
    private List<Student> students;

}
