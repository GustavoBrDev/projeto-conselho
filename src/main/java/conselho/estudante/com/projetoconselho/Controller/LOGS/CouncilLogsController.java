package conselho.estudante.com.projetoconselho.Controller.LOGS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.Council;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.CouncilLogs;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
import conselho.estudante.com.projetoconselho.SERVICES.LOGS.CouncilLogsService;
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
 * Controller para gerenciamento de logs de Councils
 * @author Gustavo Stinghen
 * @since 07/04/2025
 * @see CouncilLogsService
 */
@AllArgsConstructor
@RestController
@RequestMapping("/logs/Councils")
@Tag(name = "Logs de Counciles", description = "Recurso de gerenciamento de logs de Counciles")
public class CouncilLogsController {

    private CouncilLogsService service;

    @Operation(summary = "Busca todos os logs")
    @ApiResponse(responseCode = "200", description = "Logs encontrados com sucesso",
            content = @Content(schema = @Schema(implementation = CouncilLogs.class),
                    examples = @ExampleObject(value = "{\"id\": \"string\", \"actor\": {\"id\": 1, \"name\": \"Gustavo Stinghen\", \"registration\": 123456, \"email\": \"7G9Gt@example.com\", \"password\": \"123456\", \"image\": \"https://example.com/image.jpg\"}, \"target\": {\"id\": 1, \"name\": \"Conselho de Classe\"}, \"type\": \"CREATE\", \"timestamp\": \"2023-03-17T14:30:00.000Z\", \"createdAt\": \"2023-03-17T14:30:00.000Z\", \"changes\": [{\"field\": \"nome\", \"oldValue\": \"Conselho de Classe\", \"newValue\": \"Conselho de Classe Atualizado\"}]}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar logs")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping()
    public ResponseEntity<Page<CouncilLogs>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @Operation(summary = "Busca todos os logs realizados por um usuário")
    @ApiResponse(responseCode = "200", description = "Logs encontrados com sucesso",
            content = @Content(schema = @Schema(implementation = CouncilLogs.class),
                    examples = @ExampleObject(value = "{\"id\": \"string\", \"actor\": {\"id\": 1, \"name\": \"Gustavo Stinghen\", \"registration\": 123456, \"email\": \"7G9Gt@example.com\", \"password\": \"123456\", \"image\": \"https://example.com/image.jpg\"}, \"target\": {\"id\": 1, \"name\": \"Conselho de Classe\"}, \"type\": \"CREATE\", \"timestamp\": \"2023-03-17T14:30:00.000Z\", \"createdAt\": \"2023-03-17T14:30:00.000Z\", \"changes\": [{\"field\": \"nome\", \"oldValue\": \"Conselho de Classe\", \"newValue\": \"Conselho de Classe Atualizado\"}]}")))
    @GetMapping("/actor/")
    public ResponseEntity<Page<CouncilLogs>> findByActor(
            @RequestBody @Parameter( description = "Usuário que realizou a ação", required = true, content = @Content(schema = @Schema(implementation = User.class)), example = "{\"id\": 1, \"name\": \"Gustavo Stinghen\", \"registration\": 123456, \"email\": \"7G9Gt@example.com\", \"password\": \"123456\", \"image\": \"https://example.com/image.jpg\"}") User actor,
            Pageable pageable) {
        return ResponseEntity.ok(service.findByActor(actor, pageable));
    }

    @Operation(summary = "Busca todos os logs de uma ação")
    @ApiResponse(responseCode = "200", description = "Logs encontrados com sucesso",
            content = @Content(schema = @Schema(implementation = CouncilLogs.class),
                    examples = @ExampleObject(value = "{\"id\": \"string\", \"actor\": {\"id\": 1, \"name\": \"Gustavo Stinghen\", \"registration\": 123456, \"email\": \"7G9Gt@example.com\", \"password\": \"123456\", \"image\": \"https://example.com/image.jpg\"}, \"target\": {\"id\": 1, \"name\": \"Conselho de Classe\"}, \"type\": \"CREATE\", \"timestamp\": \"2023-03-17T14:30:00.000Z\", \"createdAt\": \"2023-03-17T14:30:00.000Z\", \"changes\": [{\"field\": \"nome\", \"oldValue\": \"Conselho de Classe\", \"newValue\": \"Conselho de Classe Atualizado\"}]}")))
    @GetMapping("/action/{type}")
    public ResponseEntity<Page<CouncilLogs>> findByType(
            @PathVariable @Parameter( description = "Tipo de ação", required = true, example = "CREATE" ) String type,
            Pageable pageable) {
        return ResponseEntity.ok(service.findByType(type, pageable));
    }

    @Operation(summary = "Busca todos os logs de um alvo")
    @ApiResponse(responseCode = "200", description = "Logs encontrados com sucesso",
            content = @Content(schema = @Schema(implementation = CouncilLogs.class),
                    examples = @ExampleObject(value = "{\"id\": \"string\", \"actor\": {\"id\": 1, \"name\": \"Gustavo Stinghen\", \"registration\": 123456, \"email\": \"7G9Gt@example.com\", \"password\": \"123456\", \"image\": \"https://example.com/image.jpg\"}, \"target\": {\"id\": 1, \"name\": \"Conselho de Classe\"}, \"type\": \"CREATE\", \"timestamp\": \"2023-03-17T14:30:00.000Z\", \"createdAt\": \"2023-03-17T14:30:00.000Z\", \"changes\": [{\"field\": \"nome\", \"oldValue\": \"Conselho de Classe\", \"newValue\": \"Conselho de Classe Atualizado\"}]}")))
    @GetMapping("/target/")
    public ResponseEntity<Page<CouncilLogs>> findByTarget(
            @RequestBody @Parameter( description = "Alvo do log", required = true, content = @Content(schema = @Schema(implementation = Council.class)), example = "{\"id\": 1, \"name\": \"Conselho de Classe\"}") Council target,
            Pageable pageable) {
        return ResponseEntity.ok(service.findByTarget(target, pageable));
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
