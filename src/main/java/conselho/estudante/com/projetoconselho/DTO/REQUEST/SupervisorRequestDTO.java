<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/DTO/REQUEST/SupervisorRequestDTO.java
package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST;


import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Supervisor;
========
package conselho.estudante.com.projetoconselho.DTO.REQUEST;


import conselho.estudante.com.projetoconselho.ENTITY.USERS.Supervisor;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/DTO/REQUEST/SupervisorRequestDTO.java
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;


/**
 * Classe model da entidade SupervisorRequestDTO
 * @author Camilly Chelest
 * @since 17/03/2025
 * @see Supervisor
 */


@Builder
public record SupervisorRequestDTO(
        @NotBlank
        String name,
        @NotBlank
        String email,
        @NotBlank
        String password,
        @NotBlank
        String image,
        @NotNull
        @Positive
        Long register
) {


    public Supervisor convert(){
        return Supervisor.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .image(this.image)
                .register(this.register)
                .build();
    }
}

