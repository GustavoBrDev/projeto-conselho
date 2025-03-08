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
public class Shift {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Date createdAt;

    @ManyToMany (mappedBy = "shifts")
    private List<Teacher> teachers;

    @OneToMany (mappedBy = "shift")
    private List<Course> course;

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

    public boolean addCourse(Course course) {

        if (this.course.contains(course)) {
            return false;
        } else {
            this.course.add(course);
            return true;
        }
    }

    public boolean removeCourse(Course course) {

        if (this.course.contains(course)) {
            this.course.remove(course);
            return true;
        } else {
            return false;
        }
    }
}
