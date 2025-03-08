package MODELS.ENTITY.USERS;

import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true) //Pedir isso ao professor
@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@Entity
public abstract class Pedadogue extends Staff{
}
