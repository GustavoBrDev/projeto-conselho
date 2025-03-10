package MODELS.ENTITY.EDUCATIONAL;


import MODELS.ENTITY.USERS.RegularUser;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class RepresentativePreCouncil extends PreCouncil {

    //Como fazer isso?
    //TODO: Perguntar ao professor sobre como usar a abstracao
    private List<RegularUser> assessable;
    private List<Feedback> feedbacks;

    //TODO: Adicionar / remover usuários e feedbacks

}
