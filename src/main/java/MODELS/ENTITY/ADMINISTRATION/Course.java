package MODELS.ENTITY.ADMINISTRATION;

import MODELS.ENTITY.USERS.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private String visualIdentity;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Integer workLoad;

    @Column(nullable = false)
    private String level;

    @ManyToMany
    private List<Subject> subjects;

    @ManyToMany (mappedBy = "courses")
    private List<Teacher> teachers;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Shift shift;

    @OneToMany (mappedBy = "course")
    @JoinColumn(nullable = false)
    private List<Classe> classes;
}
