package MODELS.ENTITY.USERS;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe model da entidade Funcionario
 * É uma subclasse de RegularUser (abstrata)
 * @author Gustavo Stinghen
 * @since 10/03/2025
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true) //Pedir isso ao professor
@Data
@MappedSuperclass
public abstract class Staff extends RegularUser {

    @Column(nullable = false)
    private Long register;
}
