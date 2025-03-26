package conselho.estudante.com.projetoconselho.Controller.CHAT;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.CHAT.TechniqueChatMessageRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ChatMessageResponseDTO;
import conselho.estudante.com.projetoconselho.SERVICES.CHAT.TechniqueChatMessageService;
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

@RestController
@RequestMapping("/chat/technique/messages")
@AllArgsConstructor
public class TechniqueChatMessageController {

    private TechniqueChatMessageService service;

    @Tag( name = "TechniqueChatMessage", description = "Recurso para gerenciamento de mensagens de chat de um técnico pedagógico" )
    @Operation(summary = "Cria uma mensagem de chat de um técnico pedagógico", description = "Cria uma mensagem de chat de um técnico pedagógico e retorna a mensagem criada com o status HTTP 201")
    @ApiResponse (responseCode = "201", description = "Mensagem de chat de um técnico pedagógico criada com sucesso",
            content = @Content(schema = @Schema(implementation = ChatMessageResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"text\": \"Hello, world!\", \"technique\": 1, \"timestamp\": \"2025-01-01T00:00:00\", \"isRead\": true, \"isDeleted\": false, \"deletedAt\": null}")))
    @ApiResponse(responseCode = "400", description = "Erro ao criar mensagem de chat de um técnico pedagógico")
    @ApiResponse (responseCode = "500", description = "Erro interno do servidor")

    @PostMapping
    public ResponseEntity<ChatMessageResponseDTO> postMessage(
           @Parameter(description = "Mensagem de chat de um técnico pedagógico", content =
           @Content(schema = @Schema(implementation = TechniqueChatMessageRequestDTO.class)),
                   required = true, example = "{" +
                   "\"text\": \"Hello, world!\", \"technique\": 1}")
           @RequestBody @Valid TechniqueChatMessageRequestDTO message) {
        try {
            return new ResponseEntity<>(service.create(message), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Tag( name = "TechniqueChatMessage", description = "Recurso para gerenciamento de mensagens de chat de um técnico pedagógico" )
    @Operation(summary = "Busca todas as mensagens de chat de um técnico pedagógico", description = "Busca todas as mensagens de chat de um técnico pedagógico e retorna com o status HTTP 200")
    @ApiResponse (responseCode = "200", description = "Mensagens de chat de um técnico pedagógico encontradas com sucesso",
            content = @Content(schema = @Schema(implementation = ChatMessageResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"text\": \"Hello, world!\", \"technique\": 1, \"timestamp\": \"2025-01-01T00:00:00\", \"isRead\": true, \"isDeleted\": false, \"deletedAt\": null}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar mensagens de chat de um técnico pedagógico")
    @ApiResponse (responseCode = "500", description = "Erro interno do servidor")

    @GetMapping
    public ResponseEntity<Page<ChatMessageResponseDTO>> findAll(
        @Parameter(description = "Filtro de mensagens de chat de um técnico pedagógico", content =
        @Content(schema = @Schema(implementation = ChatMessageResponseDTO.class)),
                required = false, example =
                "{\"id\": 1, \"text\": \"Hello, world!\", \"technique\": 1, \"timestamp\": \"2025-01-01T00:00:00\", " +
                        "\"isRead\": true, \"isDeleted\": false, \"deletedAt\": null}" )
        @PageableDefault(size = 20, sort = "timestamp") Pageable pageable) {
        try {
            return new ResponseEntity<>(service.findAll(pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //Só tem que arrumar a Service de TechniqueChatMessage para buscar o ID ao inves do technique!
    @Tag( name = "TechniqueChatMessage", description = "Recurso para gerenciamento de mensagens de chat de um técnico pedagógico" )
    @Operation(summary = "Busca todas as mensagens de chat de um técnico pedagógico", description = "Busca todas as mensagens de chat de um técnico pedagógico e retorna com o status HTTP 200")
    @ApiResponse (responseCode = "200", description = "Mensagens de chat de um técnico pedagógico encontradas com sucesso",
            content = @Content(schema = @Schema(implementation = ChatMessageResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"text\": \"Hello, world!\", \"technique\": 1, \"timestamp\": \"2025-01-01T00:00:00\", \"isRead\": true, \"isDeleted\": false, \"deletedAt\": null}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar mensagens de chat de um técnico pedagógico")
    @ApiResponse (responseCode = "500", description = "Erro interno do servidor")

    @GetMapping("/student/{id}")
    public ResponseEntity<Page<ChatMessageResponseDTO>> findByTechnique(@Parameter(description = "ID do técnico pedagógico", required = true, example = "1")
    @PathVariable Long id, @PageableDefault(size = 20, sort = "timestamp") Pageable pageable) {
        try {
            return new ResponseEntity<>(service.findByTechnique(id, pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Tag( name = "TechniqueChatMessage", description = "Recurso para gerenciamento de mensagens de chat de um técnico pedagógico" )
    @Operation(summary = "Busca uma mensagem de chat de um técnico pedagógico", description = "Busca uma mensagem de chat de um técnico pedagógico e retorna com o status HTTP 200")
    @ApiResponse (responseCode = "200", description = "Mensagem de chat de um técnico pedagógico encontrada com sucesso",
            content = @Content(schema = @Schema(implementation = ChatMessageResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"text\": \"Hello, world!\", \"technique\": 1, \"timestamp\": \"2025-01-01T00:00:00\", \"isRead\": true, \"isDeleted\": false, \"deletedAt\": null}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar mensagem de chat de um técnico pedagógico")
    @ApiResponse (responseCode = "500", description = "Erro interno do servidor")

    @GetMapping("{/id}")
    public ResponseEntity<ChatMessageResponseDTO> findById(@Parameter(description = "ID da mensagem de chat de um técnico pedagógico", required = true, example = "1")
    @PathVariable Long id) {
        try {
            return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Tag( name = "TechniqueChatMessage", description = "Recurso para gerenciamento de mensagens de chat de um técnico pedagógico" )
    @Operation(summary = "Deleta uma mensagem de chat de um técnico pedagógico", description = "Deleta uma mensagem de chat de um técnico pedagógico e retorna com o status HTTP 200")
    @ApiResponse (responseCode = "200", description = "Mensagem de chat de um técnico pedagógico deletada com sucesso",
            content = @Content(schema = @Schema(implementation = ChatMessageResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"text\": \"Hello, world!\", \"technique\": 1, \"timestamp\": \"2025-01-01T00:00:00\", \"isRead\": true, \"isDeleted\": false, \"deletedAt\": null}")))
    @ApiResponse(responseCode = "400", description = "Erro ao deletar mensagem de chat de um técnico pedagógico")
    @ApiResponse (responseCode = "500", description = "Erro interno do servidor")

    @DeleteMapping("{/id}")
    public ResponseEntity<ChatMessageResponseDTO> delete(@Parameter(description = "ID da mensagem de chat de um técnico pedagógico", required = true, example = "1")
    @PathVariable Long id) {
        try {
            return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
