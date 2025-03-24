package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.USERS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Student;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Representative;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

/**
 * @param studentIds
 * @param classeId
 */

@Builder
public record RepresentativeRequestDTO(
        @NotNull List<Long> studentIds,
        @NotNull Long classeId
) {
    public Representative convert() {
        return Representative.builder()
                .build();
    }
}