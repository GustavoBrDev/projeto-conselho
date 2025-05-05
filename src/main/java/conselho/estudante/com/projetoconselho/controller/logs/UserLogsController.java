package conselho.estudante.com.projetoconselho.controller.logs;

import conselho.estudante.com.projetoconselho.models.entity.logs.UserLogs;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import conselho.estudante.com.projetoconselho.services.logs.UserLogsService;
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
 * Controller para gerenciamento de logs de usuários
 * @author Gustavo Stinghen
 * @since 07/04/2025
 * @see UserLogsService
 */

@AllArgsConstructor
@RestController
@RequestMapping("/logs/users")
@Tag(name = "Logs de Usuários", description = "Recurso de gerenciamento de logs de usuários")
public class UserLogsController {

    private UserLogsService service;

    @Operation(summary = "Busca todos os logs")
    @ApiResponse(responseCode = "200", description = "Logs encontrados com sucesso",
            content = @Content(schema = @Schema(implementation = UserLogs.class),
            examples = @ExampleObject(value = "{\n" +
                    "    \"id\": 1,\n" +
                    "    \"user\": {\n" +
                    "        \"id\": 1,\n" +
                    "        \"name\": \"Gustavo\",\n" +
                    "        \"registration\": 123456,\n" +
                    "        \"email\": \"7G9Gt@example.com\",\n" +
                    "        \"password\": \"123456\",\n" +
                    "        \"image\": \"https://example.com/image.jpg\"\n" +
                    "    },\n" +
                    "    \"action\": \"CREATE\",\n" +
                    "    \"date\": \"2022-01-01T00:00:00\"\n" +
                    "}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar logs")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping
    public ResponseEntity<Page<UserLogs>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @Operation(summary = "Busca todos os logs realizados por um usuário")
    @ApiResponse(responseCode = "200", description = "Logs encontrados com sucesso",
            content = @Content(schema = @Schema(implementation = UserLogs.class),
            examples = @ExampleObject(value = "{\n" +
                    "    \"id\": 1,\n" +
                    "    \"user\": {\n" +
                    "        \"id\": 1,\n" +
                    "        \"name\": \"Gustavo\",\n" +
                    "        \"registration\": 123456,\n" +
                    "        \"email\": \"7G9Gt@example.com\",\n" +
                    "        \"password\": \"123456\",\n" +
                    "        \"image\": \"https://example.com/image.jpg\"\n" +
                    "    },\n" +
                    "    \"action\": \"CREATE\",\n" +
                    "    \"date\": \"2022-01-01T00:00:00\"\n" +
                    "}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar logs")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/actor/")
    public ResponseEntity<Page<UserLogs>> findByActor(
            @RequestBody @Parameter( description = "Usuário que realizou a ação", required = true, content = @Content(schema = @Schema(implementation = User.class)), example = "{\"id\": 1, \"name\": \"Gustavo Stinghen\", \"registration\": 123456, \"email\": \"7G9Gt@example.com\", \"password\": \"123456\", \"image\": \"https://example.com/image.jpg\"}") User actor,
            Pageable pageable) {
        return ResponseEntity.ok(service.findByActor(actor, pageable));
    }

    @GetMapping("/target/")
    public ResponseEntity<Page<UserLogs>> findByTarget(
            @RequestBody @Parameter( description = "Usuário alvo do log", required = true, content = @Content ( schema = @Schema ( implementation = User.class )), example = "{\"id\": 1, \"name\": \"Gustavo Stinghen\", \"registration\": 123456, \"email\": \"7G9Gt@example.com\", \"password\": \"123456\", \"image\": \"https://example.com/image.jpg\"}" ) User target,
            Pageable pageable) {
        return ResponseEntity.ok(service.findByTarget(target, pageable));
    }

    @Operation(summary = "Busca todos os logs de uma ação")
    @ApiResponse(responseCode = "200", description = "Logs encontrados com sucesso",
            content = @Content(schema = @Schema(implementation = UserLogs.class),
            examples = @ExampleObject(value = "{\n" +
                    "    \"id\": 1,\n" +
                    "    \"user\": {\n" +
                    "        \"id\": 1,\n" +
                    "        \"name\": \"Gustavo\",\n" +
                    "        \"registration\": 123456,\n" +
                    "        \"email\": \"7G9Gt@example.com\",\n" +
                    "        \"password\": \"123456\",\n" +
                    "        \"image\": \"https://example.com/image.jpg\"\n" +
                    "    },\n" +
                    "    \"action\": \"CREATE\",\n" +
                    "    \"date\": \"2022-01-01T00:00:00\"\n" +
                    "}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar logs")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/action/{type}")
    public ResponseEntity<Page<UserLogs>> findByType(
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
