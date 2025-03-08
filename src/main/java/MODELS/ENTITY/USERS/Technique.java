package MODELS.ENTITY.USERS;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true) //Pedir isso ao professor
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Technique extends Pedadogue {
}
