package MODELS.ENTITY.USERS;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.awt.*;
import java.util.Date;

@MappedSuperclass
public abstract class RegularUser {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer id;

    private String image;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Date createdAt;
}
