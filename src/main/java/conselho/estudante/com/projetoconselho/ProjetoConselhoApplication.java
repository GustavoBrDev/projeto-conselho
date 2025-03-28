package conselho.estudante.com.projetoconselho;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(
    info = @Info(
        title = "Projeto Conselho",
        version = "1.0",
        description = "Sistema de gerenciamento de conselhos de classe para o SENAI"
), tags = {
        @Tag( name = "Shift", description = "Recurso para gerenciamento de turnos" ),
        @Tag( name = "AdvisorChatMessage", description = "Recurso para gerenciamento de mensagens de chat de orientadores" ),
        @Tag( name = "StudentChatMessage", description = "Recurso para gerenciamento de mensagens de chat de estudantes" ),
        @Tag( name = "TeacherChatMessage", description = "Recurso para gerenciamento de mensagens de chat de professores" ),
        @Tag( name = "TechniqueChatMessage", description = "Recurso para gerenciamento de mensagens de chat de técnicos pedagógicos" )
}, servers = {
        @Server(
                url = "http://localhost:9090",
                description = "Servidor principal"
        )
}, externalDocs =  @ExternalDocumentation(
        description = "Diagrama de Classes",
        url = "https://lucid.app/lucidchart/a7205f80-000e-4be5-b5db-e67f5441fb02/edit?viewport_loc=1543%2C1421%2C5447%2C2669%2CHWEp-vi-RSFO&invitationId=inv_dce38466-131d-416f-8281-742e3e205518"
))
public class ProjetoConselhoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetoConselhoApplication.class, args);
    }

}
