package MODELS.ENTITY.ADMINISTRATION;

import MODELS.ENTITY.USERS.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Subject {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Integer workLoad;

    @ManyToMany ( mappedBy = "subjects" )
    private List<Teacher> teachers;

    public boolean addTeacher(Teacher teacher) {

        if (this.teachers.contains(teacher)) {
            return false;
        } else {
            this.teachers.add(teacher);
            return true;
        }
    }

    public boolean removeTeacher(Teacher teacher) {

        if (this.teachers.contains(teacher)) {
            this.teachers.remove(teacher);
            return true;
        } else {
            return false;
        }
    }
}
