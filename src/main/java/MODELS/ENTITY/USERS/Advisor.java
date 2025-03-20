package MODELS.ENTITY.USERS;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Classe model da entidade Orientador da equipe pedagógica
 * É uma subclasse de {@link Pedadogue}
 * @see Pedadogue, Staff, User
 * @author Gustavo Stinghen
 * @since 10/03/2025
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Advisor extends Pedadogue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String image;
    private String email;
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    //  @ElementCollection
    //  private List<Notification> notifications;

    private Long register;
}