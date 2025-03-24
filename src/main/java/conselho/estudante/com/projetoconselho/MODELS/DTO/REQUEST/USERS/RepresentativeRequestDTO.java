package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.USERS;

import java.util.List;

public record RepresentativeRequestDTO(
        Long classeId,
        List<Long> studentsIds
) {}
