package conselho.estudante.com.projetoconselho.models.dto.request.users;


import java.util.List;


public record RepresentativeRequestDTO(
        Long classeId,
        List<Long> studentsIds
) {}
