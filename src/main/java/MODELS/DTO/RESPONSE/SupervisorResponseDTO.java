package MODELS.DTO.RESPONSE;


import lombok.Builder;

@Builder
public record SupervisorResponseDTO(
        Long id,
        String name,
        String email,
        String password,
        String image,
        String register

) {
}

