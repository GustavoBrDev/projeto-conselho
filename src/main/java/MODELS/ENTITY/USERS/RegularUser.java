package MODELS.ENTITY.USERS;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.awt.*;
import java.util.Date;

/**
 * Abstração para usários regulares
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see User
 */
@MappedSuperclass
public abstract class RegularUser implements User {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

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
