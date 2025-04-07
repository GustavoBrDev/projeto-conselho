package conselho.estudante.com.projetoconselho.Controller.LOGS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.ClasseLogs;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
import conselho.estudante.com.projetoconselho.SERVICES.LOGS.ClassLogsService;
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
 * Controller para gerenciamento de logs de classes
 * @author Gustavo Stinghen
 * @since 07/04/2025
 * @see ClassLogsService
 */
@AllArgsConstructor
@RestController
@RequestMapping("/logs/Classs")
@Tag(name = "Logs de classes", description = "Recurso de gerenciamento de logs de classes")
public class ClasseLogsController {

    private ClassLogsService service;

    @Operation(summary = "Busca todos os logs")
    @ApiResponse(responseCode = "200", description = "Logs encontrados com sucesso",
            content = @Content(schema = @Schema(implementation = ClasseLogs.class),
                    examples = @ExampleObject(value = "{\n" +
                            "    \"id\": \"AYGUEDU\",\n" +
                            "    \"actor\": {\n" +
                            "        \"id\": 1,\n" +
                            "        \"name\": \"Gustavo Stinghen\",\n" +
                            "        \"registration\": 123456,\n" +
                            "        \"email\": \"7G9Gt@example.com\",\n" +
                            "        \"password\": \"123456\",\n" +
                            "        \"image\": \"https://example.com/image.jpg\"\n" +
                            "    },\n" +
                            "    \"target\": {\n" +
                            "        \"id\": 1,\n" +
                            "        \"name\": \"Gustavo\",\n" +
                            "        \"registration\": 123456,\n" +
                            "        \"email\": \"7G9Gt@example.com\",\n" +
                            "        \"password\": \"123456\",\n" +
                            "        \"image\": \"https://example.com/image.jpg\"\n" +
                            "    },\n" +
                            "    \"type\": \"CREATE\",\n" +
                            "    \"timestamp\": \"2022-01-01T00:00:00Z\",\n" +
                            "    \"createdAt\": \"2022-01-01T00:00:00Z\",\n" +
                            "    \"changes\": []\n" +
                            "}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar logs")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping()
    public ResponseEntity<Page<ClasseLogs>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @Operation(summary = "Busca todos os logs realizados por um usuário")
    @ApiResponse(responseCode = "200", description = "Logs encontrados com sucesso",
            content = @Content(schema = @Schema(implementation = ClasseLogs.class),
                    examples = @ExampleObject(value = "{\n" +
                            "    \"id\": \"AYGUEDU\",\n" +
                            "    \"actor\": {\n" +
                            "        \"id\": 1,\n" +
                            "        \"name\": \"Gustavo Stinghen\",\n" +
                            "        \"registration\": 123456,\n" +
                            "        \"email\": \"7G9Gt@example.com\",\n" +
                            "        \"password\": \"123456\",\n" +
                            "        \"image\": \"https://example.com/image.jpg\"\n" +
                            "    },\n" +
                            "    \"target\": {\n" +
                            "        \"id\": 1,\n" +
                            "        \"name\": \"Gustavo\",\n" +
                            "        \"registration\": 123456,\n" +
                            "        \"email\": \"7G9Gt@example.com\",\n" +
                            "        \"password\": \"123456\",\n" +
                            "        \"image\": \"https://example.com/image.jpg\"\n" +
                            "    },\n" +
                            "    \"type\": \"CREATE\",\n" +
                            "    \"timestamp\": \"2022-01-01T00:00:00Z\",\n" +
                            "    \"createdAt\": \"2022-01-01T00:00:00Z\",\n" +
                            "    \"changes\": []\n" +
                            "}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar logs")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/actor/")
    public ResponseEntity<Page<ClasseLogs>> findByActor(
            @RequestBody @Parameter(description = "Usuário que realizou a ação", required = true, content = @Content(schema = @Schema(implementation = User.class)), example = "{\"id\": 1, \"name\": \"Gustavo Stinghen\", \"registration\": 123456, \"email\": \"7G9Gt@example.com\", \"password\": \"123456\", \"image\": \"https://example.com/image.jpg\"}") User actor,
            Pageable pageable) {
        return ResponseEntity.ok(service.findByActor(actor, pageable));
    }

    @Operation(summary = "Busca todos os logs de um usuário")
    @ApiResponse(responseCode = "200", description = "Logs encontrados com sucesso",
            content = @Content(schema = @Schema(implementation = ClasseLogs.class),
                    examples = @ExampleObject(value = "{\n" +
                            "    \"id\": 1,\n" +
                            "    \"actor\": {\n" +
                            "        \"id\": 1,\n" +
                            "        \"nome\": \"Gustavo Stinghen\",\n" +
                            "        \"matricula\": \"123456\",\n" +
                            "        \"email\": \"gustavo@example.com\",\n" +
                            "        \"senha\": \"123456\",\n" +
                            "        \"imagem\": \"https://example.com/image.jpg\"\n" +
                            "    },\n" +
                            "    \"target\": {\n" +
                            "        \"id\": 1,\n" +
                            "        \"nome\": \"Gustavo\",\n" +
                            "        \"matricula\": \"123456\",\n" +
                            "        \"email\": \"gustavo@example.com\",\n" +
                            "        \"senha\": \"123456\",\n" +
                            "        \"imagem\": \"https://example.com/image.jpg\"\n" +
                            "    },\n" +
                            "    \"tipo\": \"CREATE\",\n" +
                            "    \"dataCriacao\": \"2022-01-01T00:00:00Z\",\n" +
                            "    \"alteracoes\": []\n" +
                            "}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar logs")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/target/")
    public ResponseEntity<Page<ClasseLogs>> findByTarget(
            @RequestBody @Parameter(description = "Usuário alvo do log", required = true, content = @Content(schema = @Schema(implementation = Classe.class)), example = "{\"id\": 1, \"nome\": \"Gustavo\", \"matricula\": \"123456\", \"email\": \"gustavo@example.com\", \"senha\": \"123456\", \"imagem\": \"https://example.com/image.jpg\"}") Classe target,
            Pageable pageable) {
        return ResponseEntity.ok(service.findByTarget(target, pageable));
    }

    @Operation(summary = "Busca todos os logs por tipo")
    @ApiResponse(responseCode = "200", description = "Logs encontrados com sucesso",
            content = @Content(schema = @Schema(implementation = ClasseLogs.class),
                    examples = @ExampleObject(value = "{\n" +
                            "    \"id\": 1,\n" +
                            "    \"actor\": {\n" +
                            "        \"id\": 1,\n" +
                            "        \"nome\": \"Gustavo Stinghen\",\n" +
                            "        \"matricula\": \"123456\",\n" +
                            "        \"email\": \"gustavo@example.com\",\n" +
                            "        \"senha\": \"123456\",\n" +
                            "        \"imagem\": \"https://example.com/image.jpg\"\n" +
                            "    },\n" +
                            "    \"target\": {\n" +
                            "        \"id\": 1,\n" +
                            "        \"nome\": \"Gustavo\",\n" +
                            "        \"matricula\": \"123456\",\n" +
                            "        \"email\": \"gustavo@example.com\",\n" +
                            "        \"senha\": \"123456\",\n" +
                            "        \"imagem\": \"https://example.com/image.jpg\"\n" +
                            "    },\n" +
                            "    \"tipo\": \"CREATE\",\n" +
                            "    \"dataCriacao\": \"2022-01-01T00:00:00Z\",\n" +
                            "    \"alteracoes\": []\n" +
                            "}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar logs")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/type/")
    public ResponseEntity<Page<ClasseLogs>> findByType(
            @RequestParam @Parameter(description = "Tipo do log", required = true, example = "CREATE") String type,
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
