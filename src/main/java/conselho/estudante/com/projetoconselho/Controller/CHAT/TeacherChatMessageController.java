package conselho.estudante.com.projetoconselho.Controller.CHAT;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.CHAT.TeacherChatMessageRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ChatMessageResponseDTO;
import conselho.estudante.com.projetoconselho.SERVICES.CHAT.TeacherChatMessageService;
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
 * Controller para gerenciamento de mensagens de chat de professores
 * @author Cauã Dutra
 * @since 24/03/2025
 * @see TeacherChatMessageService
 *
 * Atualizado em 28/03/2025
 * Adicionado uma tag única e modificado service conforme
 * @author Gustavo Stinghen
 */

@RestController
@RequestMapping("/chat/teacher/messages")
@AllArgsConstructor
@Tag( name = "TeacherChatMessage", description = "Recurso para gerenciamento de mensagens de chat de professores" )
public class TeacherChatMessageController {

    private TeacherChatMessageService service;

    @Operation(summary = "Cria uma mensagem de chat de professores", description = "Cria uma mensagem de chat de professores e retorna a mensagem criada com o status HTTP 201" )
    @ApiResponse (responseCode = "201", description = "Mensagem de chat de professores criada com sucesso",
            content = @Content(schema = @Schema(implementation = ChatMessageResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"text\": \"Hello, world!\", \"teacher\": 1, \"timestamp\": \"2025-01-01T00:00:00\", \"isRead\": true, \"isDeleted\": false, \"deletedAt\": null}")))
    @ApiResponse(responseCode = "400", description = "Erro ao criar mensagem de chat de professores")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PostMapping
    public ResponseEntity<ChatMessageResponseDTO> postMessage(@Parameter(description = "Mensagem de chat de professores", content =
           @Content(schema = @Schema(implementation = TeacherChatMessageRequestDTO.class)),
           required = true, example = "{" +
           "\"text\": \"Hello, world!\", \"teacher\": 1}")
    @RequestBody @Valid TeacherChatMessageRequestDTO message) {
        try {
            return new ResponseEntity<>(service.create(message), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Busca todas as mensagens de chat de professores", description = "Busca todas as mensagens de chat de professores e retorna com o status HTTP 200" )
    @ApiResponse (responseCode = "200", description = "Mensagens de chat de professores encontradas com sucesso",
            content = @Content(schema = @Schema(implementation = ChatMessageResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"text\": \"Hello, world!\", \"teacher\": 1, \"timestamp\": \"2025-01-01T00:00:00\", \"isRead\": true, \"isDeleted\": false, \"deletedAt\": null}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar mensagens de chat de professores")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping
    public ResponseEntity<Page<ChatMessageResponseDTO>> findAll(
           @Parameter(description = "Filtro de mensagens de chat de professores", content =
           @Content(schema = @Schema(implementation = ChatMessageResponseDTO.class)),
           required = false, example =
           "{\"id\": 1, \"text\": \"Hello, world!\", \"teacher\": 1, \"timestamp\": \"2025-01-01T00:00:00\", \"isRead\": true, \"isDeleted\": false, \"deletedAt\": null}" )
           @PageableDefault(size = 20, sort = "timestamp") Pageable pageable) {
        try {
            return new ResponseEntity<>(service.findAll(pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Busca todas as mensagens de chat de um professor em específico", description = "Busca todas as mensagens de chat de professores e retorna com o status HTTP 200" )
    @ApiResponse (responseCode = "200", description = "Mensagens de chat de professores encontradas com sucesso",
            content = @Content(schema = @Schema(implementation = ChatMessageResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"text\": \"Hello, world!\", \"teacher\": 1, \"timestamp\": \"2025-01-01T00:00:00\", \"isRead\": true, \"isDeleted\": false, \"deletedAt\": null}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar mensagens de chat de professores")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/student/{id}")
    public ResponseEntity<Page<ChatMessageResponseDTO>> findByTeacher(
            @Parameter(description = "ID do professor", required = true, example = "1")
            @PathVariable Long id, @PageableDefault(size = 20, sort = "timestamp") Pageable pageable) {
        try {
            return new ResponseEntity<>(service.findByTeacher(id, pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Busca uma mensagem de chat de professores", description = "Busca uma mensagem de chat de professores e retorna com o status HTTP 200" )
    @ApiResponse (responseCode = "200", description = "Mensagem de chat de professores encontrada com sucesso",
            content = @Content(schema = @Schema(implementation = ChatMessageResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"text\": \"Hello, world!\", \"teacher\": 1, \"timestamp\": \"2025-01-01T00:00:00\", \"isRead\": true, \"isDeleted\": false, \"deletedAt\": null}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar mensagem de chat de professores")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/{id}")
    public ResponseEntity<ChatMessageResponseDTO> findById(
           @Parameter(description = "ID da mensagem de chat de professores", required = true, example = "1")
           @PathVariable Long id) {
        try {
            return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Deleta uma mensagem de chat de professores", description = "Deleta uma mensagem de chat de professores e retorna com o status HTTP 200" )
    @ApiResponse (responseCode = "200", description = "Mensagem de chat de professores deletada com sucesso",
            content = @Content(schema = @Schema(implementation = ChatMessageResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"text\": \"Hello, world!\", \"teacher\": 1, \"timestamp\": \"2025-01-01T00:00:00\", \"isRead\": true, \"isDeleted\": false, \"deletedAt\": null}")))
    @ApiResponse (responseCode = "400", description = "Erro ao deletar mensagem de chat de professores")
    @ApiResponse (responseCode = "500", description = "Erro interno do servidor")
    @DeleteMapping("/{id}")
    public ResponseEntity<ChatMessageResponseDTO> delete(
           @Parameter(description = "ID da mensagem de chat de professores", required = true, example = "1")
           @PathVariable Long id) {
        try {
            return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
