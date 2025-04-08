package conselho.estudante.com.projetoconselho.controller.logs;

import conselho.estudante.com.projetoconselho.models.entity.chat.ChatMessage;
import conselho.estudante.com.projetoconselho.models.entity.logs.ChatMessageLogs;
import conselho.estudante.com.projetoconselho.services.logs.ChatMessageLogsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para gerenciamento de logs de mensagens de chat
 * @author Gustavo Stinghen
 * @since 07/04/2025
 * @see ChatMessageLogsService
 */

@AllArgsConstructor
@RestController
@RequestMapping("/logs/chat-message-logs")
@Tag(name = "ChatMessageLogs", description = "Gerenciamento de logs de mensagens de chat")
public class ChatMessageLogsController {

    private ChatMessageLogsService service;

    @Operation(summary = "Busca todos os logs")
    @ApiResponse(responseCode = "200", description = "Logs encontrados com sucesso",
            content = @Content(schema = @Schema(implementation = ChatMessageLogs.class),
                    examples = @ExampleObject(value = "{\n" +
                            "    \"id\": 1,\n" +
                            "    \"text\": \"Olá, como posso ajudar?\",\n" +
                            "    \"student\": {\n" +
                            "        \"id\": 1,\n" +
                            "        \"name\": \"João Silva\"\n" +
                            "    },\n" +
                            "    \"timestamp\": \"2023-03-17T14:30:00Z\",\n" +
                            "    \"isRead\": false,\n" +
                            "    \"isDeleted\": false,\n" +
                            "    \"deletedAt\": null\n" +
                            "}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar logs")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping()
    public ResponseEntity<Page<ChatMessageLogs>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/target/")
    public ResponseEntity<Page<ChatMessageLogs>> findByTarget(
            @RequestBody @Parameter( description = "Mensagem alvo do log", required = true, content = @Content ( schema = @Schema ( implementation = ChatMessage.class )), example = "{\"id\": 1, \"name\": \"Gustavo Stinghen\", \"registration\": 123456, \"email\": \"7G9Gt@example.com\", \"password\": \"123456\", \"image\": \"https://example.com/image.jpg\"}" ) ChatMessage target,
            Pageable pageable) {
        return ResponseEntity.ok(service.findByTarget(target, pageable));
    }

    @Operation(summary = "Busca todos os logs de uma ação")
    @ApiResponse(responseCode = "200", description = "Logs encontrados com sucesso",
            content = @Content(schema = @Schema(implementation = ChatMessageLogs.class),
                    examples = @ExampleObject(value = "{\n" +
                            "    \"id\": 1,\n" +
                            "    \"text\": \"Olá, como posso ajudar?\",\n" +
                            "    \"student\": {\n" +
                            "        \"id\": 1,\n" +
                            "        \"name\": \"João Silva\"\n" +
                            "    },\n" +
                            "    \"timestamp\": \"2023-03-17T14:30:00Z\",\n" +
                            "    \"isRead\": false,\n" +
                            "    \"isDeleted\": false,\n" +
                            "    \"deletedAt\": null\n" +
                            "}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar logs")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/action/{type}")
    public ResponseEntity<Page<ChatMessageLogs>> findByType(
            @PathVariable @Parameter( description = "Tipo de ação", required = true, example = "CREATE" ) String type,
            Pageable pageable) {
        return ResponseEntity.ok(service.findByType(type, pageable));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um log")
    @ApiResponse(responseCode = "204", description = "Log deletado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao deletar log")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> delete(@PathVariable @Parameter( description = "Id do log", required = true, example = "AYGUEDU" ) String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
