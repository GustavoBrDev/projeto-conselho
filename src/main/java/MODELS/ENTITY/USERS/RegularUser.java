package MODELS.ENTITY.USERS;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

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

    public List<Notification> notifications;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Date createdAt;
}
