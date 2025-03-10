package MODELS.ENTITY.USERS;

import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Abstração da equipe pedagógica
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see Staff, User
 */
@EqualsAndHashCode(callSuper = true) //Pedir isso ao professor
@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@Entity
public abstract class Pedadogue extends Staff{
}
