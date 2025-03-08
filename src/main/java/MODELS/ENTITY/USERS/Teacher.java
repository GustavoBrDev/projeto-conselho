package MODELS.ENTITY.USERS;

import MODELS.ENTITY.ADMINISTRATION.Course;
import MODELS.ENTITY.ADMINISTRATION.Shift;
import MODELS.ENTITY.ADMINISTRATION.Subject;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode (callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Teacher extends Staff {

    @ManyToMany
    private List<Course> courses;

    @ManyToMany
    private List<Subject> subjects;

    @ManyToMany
    private List<Shift> shifts;

    public boolean addCourse(Course course) {

        if (this.courses.contains(course)) {
            return false;
        } else {
            this.courses.add(course);
            return true;
        }

    }

    public boolean removeCourse(Course course) {

        if (this.courses.contains(course)) {
            this.courses.remove(course);
            return true;
        } else {
            return false;
        }

    }

    public boolean addSubject(Subject subject) {

        if (this.subjects.contains(subject)) {
            return false;
        } else {
            this.subjects.add(subject);
            return true;
        }
    }

    public boolean removeSubject(Subject subject) {

        if (this.subjects.contains(subject)) {
            this.subjects.remove(subject);
            return true;
        } else {
            return false;
        }

    }

    public boolean addShift(Shift shift) {

        if (this.shifts.contains(shift)) {
            return false;
        } else {
            this.shifts.add(shift);
            return true;
        }

    }

    public boolean removeShift(Shift shift) {

        if (this.shifts.contains(shift)) {
            this.shifts.remove(shift);
            return true;
        } else {
            return false;
        }

    }
}
