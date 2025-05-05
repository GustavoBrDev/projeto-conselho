package conselho.estudante.com.projetoconselho;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableMongoRepositories
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
        @Tag( name = "TechniqueChatMessage", description = "Recurso para gerenciamento de mensagens de chat de técnicos pedagógicos" ),
        @Tag(name = "Login", description = "Operações de login de usuarios"),
        @Tag(name = "Estudantes", description = "Cria um novo estudante"),
        @Tag(name = "Supervisor", description = "Recurso para gerenciamento de supervisores"),
        @Tag(name = "Teacher", description = "Recurso para gerenciamento de professores"),
        @Tag(name = "Technique", description = "Recurso para gerenciamento de tecnicos"),
        @Tag(name = "TeacherPreCouncils", description = "Recurso para o gerenciamento de TeacherPreCouncils"),
        @Tag(name = "Cursos", description = "Recurso para gerenciamento de cursos"),
        @Tag(name = "Subject", description = "Recurso para gerenciamento de disciplinas"),
        @Tag(name = "Classe", description = "Controlador para gerenciar as operações relacionadas a Classe"),
        @Tag(name = "Logs de Usuários", description = "Recurso de gerenciamento de logs de usuários"),
        @Tag(name = "ChatMessageLogs", description = "Gerenciamento de logs de mensagens de chat"),
        @Tag(name = "Logs de classes", description = "Recurso de gerenciamento de logs de classes"),
        @Tag(name = "Logs de Conselhos", description = "Recurso de gerenciamento de logs de Conselhos"),
        @Tag(name = "Logs de Cursos", description = "Recurso de gerenciamento de logs de cursos"),
        @Tag(name = "Logs de Feedbacks", description = "Recurso de gerenciamento de logs de Feedbacks"),
        @Tag(name = "Logs de Logins", description = "Recurso de gerenciamento de logs de Logins"),
        @Tag(name = "Logs de PreCouncils", description = "Recurso de gerenciamento de logs de PreCouncils"),
        @Tag(name = "Logs de representantes", description = "Recurso de gerenciamento de logs de representantes"),
        @Tag(name = "Logs de Shifts", description = "Recurso de gerenciamento de logs de Shifts"),
        @Tag(name = "Logs de Subjects", description = "Recurso de gerenciamento de logs de Subjects"),
        @Tag( name = "Feedbacks de orientadores", description = "Gerenciamento de feedbacks de orientadores" ),
        @Tag(name = "Item Feedback", description = "Gerenciamento de feedbacks de itens"),
        @Tag(name = "Personal Feedbacks", description = "Gerenciamento de feedbacks pessoais"),
        @Tag(name = "Teacher Feedback", description = "Recurso para gerenciar feedbacks de professores")

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
