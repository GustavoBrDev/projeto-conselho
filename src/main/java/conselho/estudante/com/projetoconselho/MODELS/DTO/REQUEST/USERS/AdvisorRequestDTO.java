package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.USERS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Advisor;
import java.util.Date;

public record AdvisorRequestDTO(String name, String email, String password, Long register) {
    public Advisor convert() {
        return Advisor.builder()
                .name(name)
                .email(email)
                .password(password)
                .register(register)
                .createdAt(new Date())
                .username(email)
                .build();
    }
}