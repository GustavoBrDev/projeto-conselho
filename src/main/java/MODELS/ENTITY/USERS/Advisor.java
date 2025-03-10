package MODELS.ENTITY.USERS;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe model da entidade Orientador da equipe pedagógica
 * É uma subclasse de {@link Pedadogue}
 * @see Pedadogue, Staff, User
 * @author Gustavo Stinghen
 * @since 10/03/2025
 */
@EqualsAndHashCode(callSuper = true) //Pedir isso ao professor
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Advisor extends Pedadogue {
}
