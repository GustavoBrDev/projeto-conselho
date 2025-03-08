package MODELS.ENTITY.EDUCATIONAL;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Council {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer id;
}
