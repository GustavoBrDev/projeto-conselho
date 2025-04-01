package conselho.estudante.com.projetoconselho.Controller.ADMINISTRATION;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.ADMINISTRATION.SubjectRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ADMINISTRATION.SubjectResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Subject;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
import conselho.estudante.com.projetoconselho.SERVICES.ADMINISTRATION.SUBJECT.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @Tag(name = "Disciplina")
    @Operation(summary = "Crie uma disciplina")
    @ApiResponse(responseCode = "201", description = "Disciplina criado com sucesso"
            , content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class))
             examples = @ExampleObject(value = "{\"nome\": \"Matematica\", \"workLoad\": 80}"))
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @PostMapping
    public ResponseEntity<SubjectResponseDTO> create(@RequestBody SubjectRequestDTO subjectRequestDTO, @RequestParam User actor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectService.create
                (subjectRequestDTO, actor));
    }

    @Tag(name = "Disciplina")
    @Operation(summary = "Atualizar uma disciplina")
    @ApiResponse(responseCode = "200", description = "Disciplina atualizada com sucesso, retorna o objeto ATUALIZADO"
            , content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class),
            examples = @ExampleObject(value = "{\"nome\": \"Matematica\", \"workLoad\": 80}")))
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @PutMapping("/{id}")
    public ResponseEntity<SubjectResponseDTO> update(@PathVariable Long id, @RequestBody SubjectRequestDTO subjectRequestDTO, @RequestParam User actor) {
        return ResponseEntity.ok(subjectService.update
                (id, subjectRequestDTO, actor));
    }

    @Tag(name = "Disciplina")
    @Operation(summary = "Disciplina editada")
    @ApiResponse(responseCode = "200", description = "Disciplina editada com sucesso"
            , content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class))
            examples = @ExampleObject(value = "{\"name\": \"Matematica\", \"workLoad\": 80}"))
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/{id}/name")
    public ResponseEntity<SubjectResponseDTO> editName(@PathVariable Long id, @RequestParam String name, @RequestParam User actor) {
        return ResponseEntity.ok(subjectService.editName
                (id, name, actor));
    }

    @Tag(name = "Disciplina")
    @Operation(summary = "Editando carga horária")
    @ApiResponse(responseCode = "200", description = "Disciplina encontrada com sucesso"
            , content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class))
            examples = @ExampleObject(value = "{\"name\": \"Matematica\", \"workLoad\": 80}"))
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/{id}/workload")
    public ResponseEntity<SubjectResponseDTO> editWorkLoad(@PathVariable Long id, @RequestParam Integer workLoad, @RequestParam User actor) {
        return ResponseEntity.ok(subjectService.editWorkLoad
                (id, workLoad, actor));
    }

    @Tag(name = "Disciplina")
    @Operation(summary = "Disciplina encontrada ")
    @ApiResponse(responseCode = "200", description = "Disciplina encontrada com sucesso"
            , content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class))
            examples = @ExampleObject(value = "{\"name\": \"Matematica\", \"workLoad\": 80}"))
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @GetMapping
    public ResponseEntity<Page<SubjectResponseDTO>> findSubjects(Pageable pageable) {
        return ResponseEntity.ok(subjectService.findSubjects
                (pageable));
    }

    @Tag(name = "Disciplina")
    @Operation(summary = "Disciplina encontrada por ID")
    @ApiResponse(responseCode = "200", description = "Disciplina encontrada com sucesso"
            , content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class))
            examples = @ExampleObject(value = "{\"name\": \"Matematica\", \"workLoad\": 80}"))
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponseDTO> findSubjectById(@PathVariable Long id) {
        return ResponseEntity.ok
                (subjectService.findSubjectById(id));
    }

    @Tag(name = "Disciplina")
    @Operation(summary = "Disciplina encontrada por ID")
    @ApiResponse(responseCode = "200", description = "Disciplina encontrada com sucesso"
            , content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class))
            examples = @ExampleObject(value = "{\"name\": \"Matematica\", \"workLoad\": 80}"))
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/{id}/object")
    public ResponseEntity<Subject> getObjectSubject(@PathVariable Long id) {
        return ResponseEntity.ok
                (subjectService.getObjectSubject(id));
    }

    @Tag(name = "Disciplina")
    @Operation(summary = "Delete a disciplina")
    @ApiResponse(responseCode = "204", description = "Disciplina deletada com sucesso, retorna o objeto DELETADO")
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subjectService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Tag(name = "Disciplina")
    @Operation(summary = "Disciplinas filtradas")
    @ApiResponse(responseCode = "200", description = "Disciplinas filtradas com sucesso, retorna o objeto FILTRA"
            , content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class))
            examples = @ExampleObject(value = "{\"name\": \"Matematica\", \"workLoad\": 80}"))
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/filter")
    public ResponseEntity<Page<SubjectResponseDTO>> subjectFilter(@RequestParam String termo, Pageable pageable) {
        return ResponseEntity.ok(subjectService.subjectFilter
                (termo, pageable));
    }

    @Tag(name = "Disciplina")
    @Operation(summary = "Disciplinas encontradas")
    @ApiResponse(responseCode = "200", description = "Disciplinas encontradas com sucesso"
            , content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class))
            examples = @ExampleObject(value = "{\"name\": \"Matematica\", \"workLoad\": 80}"))
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<Page<SubjectResponseDTO>> findSubjectsByTeacher(@PathVariable Long teacherId, Pageable pageable) {
        return ResponseEntity.ok(subjectService.findSubjectsByTeacher
                (teacherId, pageable));
    }
}
