<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/ENTITY/USERS/Technique.java
package conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS;

import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.TechniqueResponseDTO;
========
package conselho.estudante.com.projetoconselho.ENTITY.USERS;

import conselho.estudante.com.projetoconselho.DTO.RESPONSE.TechniqueResponseDTO;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/ENTITY/USERS/Technique.java
import jakarta.persistence.Entity;
import lombok.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Classe model da entidade Técnico da equipe pedagógica
 * @see User
 * @author Gustavo Stinghen
 * @since 10/03/2025
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Technique implements User {

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

    @Column(nullable = false)
    private Long register;

    /**
     * Converte a entidade Technique em um DTO de resposta TechniqueResponseDTO.
     *
     * @return Uma instância de TechniqueResponseDTO contendo os dados desta entidade.
     * @see TechniqueResponseDTO
     */
    public TechniqueResponseDTO toDTO() {
        return new TechniqueResponseDTO(
                this.id,
                this.name,
                this.image,
                this.email,
                this.register
        );
    }
}
