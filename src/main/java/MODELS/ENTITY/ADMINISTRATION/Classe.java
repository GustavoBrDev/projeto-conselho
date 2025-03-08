package MODELS.ENTITY.ADMINISTRATION;

import MODELS.ENTITY.USERS.Representative;
import MODELS.ENTITY.USERS.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Classe {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String acronym;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Course course;

    @ManyToMany
    private List<Student> students;

    @OneToOne
    @JoinColumn(nullable = false)
    private Representative representative;

    @Column(nullable = false)
    private boolean active;

    public boolean addStudent(Student student) {

        if (this.students.contains(student)) {
            return false;
        } else {
            this.students.add(student);
            return true;
        }

    }

    public boolean removeStudent(Student student) {

        if (this.students.contains(student)) {
            this.students.remove(student);
            return true;
        } else {
            return false;
        }

    }
}
