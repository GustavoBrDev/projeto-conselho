package conselho.estudante.com.projetoconselho.Controller.CHAT;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.CHAT.AdvisorChatMessageRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ChatMessageResponseDTO;
import conselho.estudante.com.projetoconselho.SERVICES.CHAT.AdvisorChatMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



/**
 * Controller para gerenciamento de mensagens de chat de orientadores
 @author Cauã Dutra
 @since 24/03/2025
 @see AdvisorChatMessageService
 */

@RestController
@RequestMapping("/chat/advisor/messages")
@AllArgsConstructor
public class AdvisorChatMessageController {

    private AdvisorChatMessageService service;

    @Tag( name = "AdvisorChatMessage", description = "Recurso para gerenciamento de mensagens de chat de orientadores" )
    @Operation(summary = "Cria uma mensagem de chat de orientadores", description = "Cria uma mensagem de chat de orientadores e retorna a mensagem criada com o status HTTP 201" )
    @ApiResponse (responseCode = "201", description = "Mensagem de chat de orientadores criada com sucesso",
            content = @Content(schema = @Schema(implementation = ChatMessageResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"text\": \"Hello, world!\", \"advisor\": 1, \"timestamp\": \"2025-01-01T00:00:00\", \"isRead\": true, \"isDeleted\": false, \"deletedAt\": null}")))
    @ApiResponse(responseCode = "400", description = "Erro ao criar mensagem de chat de orientadores")
    @ApiResponse (responseCode = "500", description = "Erro interno do servidor")
    @PostMapping
    public ResponseEntity<ChatMessageResponseDTO> postMessage(
            @Parameter(description = "Mensagem de chat de orientadores", content =
                    @Content(schema = @Schema(implementation = AdvisorChatMessageRequestDTO.class)),
                    required = true, example = "{" +
                    "\"text\": \"Hello, world!\", \"advisor\": 1}")
            @RequestBody @Valid AdvisorChatMessageRequestDTO message) {
        try {
            return new ResponseEntity<>(service.create(message), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Tag( name = "AdvisorChatMessage", description = "Recurso para gerenciamento de mensagens de chat de orientadores" )
    @Operation (summary = "Busca todas as mensagens de chat de orientadores", description = "Busca todas as mensagens de chat de orientadores e retorna com o status HTTP 200" )
    @ApiResponse (responseCode = "200", description = "Mensagens de chat de orientadores encontradas com sucesso",
            content = @Content(schema = @Schema(implementation = ChatMessageResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"text\": \"Hello, world!\", \"advisor\": 1, \"timestamp\": \"2025-01-01T00:00:00\", \"isRead\": true, \"isDeleted\": false, \"deletedAt\": null}")))
    @ApiResponse (responseCode = "400", description = "Erro ao buscar mensagens de chat de orientadores")
    @ApiResponse (responseCode = "500", description = "Erro interno do servidor")
    @GetMapping
    public ResponseEntity<Page<ChatMessageResponseDTO>> findAll(
            @Parameter(description = "Filtro de mensagens de chat de orientadores", content =
                    @Content(schema = @Schema(implementation = ChatMessageResponseDTO.class)),
                    example = "{\"id\": 1, \"text\": \"Hello, world!\", \"advisor\": 1, \"timestamp\": " +
                            "\"2025-01-01T00:00:00\", \"isRead\": true, \"isDeleted\": false, \"deletedAt\": null}")
            @PageableDefault(size = 20, sort = "timestamp") Pageable pageable) {
        try {
            return new ResponseEntity<>(service.findAll(pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //Só tem que arrumar a Service de AdvisorChatMessage para buscar o ID ao inves do student!
    @Tag( name = "AdvisorChatMessage", description = "Recurso para gerenciamento de mensagens de chat de orientadores" )
    @Operation (summary = "Busca todas as mensagens de chat de um orientador em específico", description = "Busca todas as mensagens de chat de orientadores e retorna com o status HTTP 200" )
    @ApiResponse (responseCode = "200", description = "Mensagens de chat de orientadores encontradas com sucesso",
            content = @Content(schema = @Schema(implementation = ChatMessageResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"text\": \"Hello, world!\", \"advisor\": 1, \"timestamp\": \"2025-01-01T00:00:00\", \"isRead\": true, \"isDeleted\": false, \"deletedAt\": null}")))
    @ApiResponse (responseCode = "400", description = "Erro ao buscar mensagens de chat de orientadores")
    @ApiResponse (responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/student/{id}")
    public ResponseEntity<Page<ChatMessageResponseDTO>> findByAdvisor(
            @Parameter(description = "ID do orientador", required = true, example = "1")
            @PathVariable Long id, @PageableDefault(size = 20, sort = "timestamp") Pageable pageable) {
        try {
            return new ResponseEntity<>(service.findByAdvisor(id, pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Tag( name = "AdvisorChatMessage", description = "Recurso para gerenciamento de mensagens de chat de orientadores" )
    @Operation (summary = "Busca uma mensagem de chat de orientadores", description = "Busca uma mensagem de chat de orientadores e retorna com o status HTTP 200" )
    @ApiResponse (responseCode = "200", description = "Mensagem de chat de orientadores encontrada com sucesso",
            content = @Content(schema = @Schema(implementation = ChatMessageResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"text\": \"Hello, world!\", \"advisor\": 1, \"timestamp\": \"2025-01-01T00:00:00\", \"isRead\": true, \"isDeleted\": false, \"deletedAt\": null}")))
    @ApiResponse (responseCode = "400", description = "Erro ao buscar mensagem de chat de orientadores")
    @ApiResponse (responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("{/id}")
    public ResponseEntity<ChatMessageResponseDTO> findById(
            @Parameter(description = "ID da mensagem de chat de orientadores", required = true, example = "1")
            @PathVariable Long id) {
        try {
            return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Tag( name = "AdvisorChatMessage", description = "Recurso para gerenciamento de mensagens de chat de orientadores" )
    @Operation (summary = "Deleta uma mensagem de chat de orientadores", description = "Deleta uma mensagem de chat de orientadores e retorna com o status HTTP 200" )
    @ApiResponse (responseCode = "200", description = "Mensagem de chat de orientadores deletada com sucesso",
            content = @Content(schema = @Schema(implementation = ChatMessageResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"text\": \"Hello, world!\", \"advisor\": 1, \"timestamp\": \"2025-01-01T00:00:00\", \"isRead\": true, \"isDeleted\": true, \"deletedAt\": \"2025-01-01T00:00:00\"}")))
    @ApiResponse (responseCode = "400", description = "Erro ao deletar mensagem de chat de orientadores")
    @ApiResponse (responseCode = "500", description = "Erro interno do servidor")
    @DeleteMapping("{/id}")
    public ResponseEntity<ChatMessageResponseDTO> delete(
            @Parameter(description = "ID da mensagem de chat de orientadores", required = true, example = "1")
            @PathVariable Long id) {
        try {
            return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
