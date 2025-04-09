package conselho.estudante.com.projetoconselho.controller.administration;

import conselho.estudante.com.projetoconselho.models.dto.request.administration.SubjectRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.administration.SubjectResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import conselho.estudante.com.projetoconselho.services.administration.subject.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@Tag(name = "Subject", description = "Recurso para gerenciamento de disciplinas")
public class SubjectController {

    private final SubjectService subjectService;


    @Operation(summary = "Crie uma disciplina")
    @ApiResponse(responseCode = "201", description = "Disciplina criado com sucesso"
            , content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class),
             examples = @ExampleObject(value = "{\"nome\": \"Matematica\", \"workLoad\": 80}")))
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @PostMapping
    public ResponseEntity<SubjectResponseDTO> create(@RequestBody @Parameter(description = "Disciplina a ser criada") SubjectRequestDTO subjectRequestDTO,
                                                     @Parameter(description = "Usuário que está criando a disciplina") @RequestParam User actor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectService.create
                (subjectRequestDTO, actor));
    }

    @Operation(summary = "Atualizar uma disciplina")
    @ApiResponse(responseCode = "200", description = "Disciplina atualizada com sucesso, retorna o objeto ATUALIZADO"
            , content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class),
            examples = @ExampleObject(value = "{\"nome\": \"Matematica\", \"workLoad\": 80}")))
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @PutMapping("/{id}")
    public ResponseEntity<SubjectResponseDTO> update(@PathVariable @Parameter(description = "ID da disciplina" ) Long id,
                                                     @RequestBody @Parameter(description = "Novos dados da disciplina" ) SubjectRequestDTO subjectRequestDTO,
                                                     @RequestParam @Parameter(description = "Usuário que está atualizando a disciplina" ) User actor) {
        return ResponseEntity.ok(subjectService.update
                (id, subjectRequestDTO, actor));
    }


    @Operation(summary = "Disciplina editada")
    @ApiResponse(responseCode = "200", description = "Subject edited successfully"
            , content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class),
            examples = @ExampleObject(value = "{\"nome\": \"Matematica\", \"workLoad\": 80}")))
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/{id}/name")
    public ResponseEntity<SubjectResponseDTO> editName(@PathVariable @Parameter(description = "ID da disciplina" ) Long id,
                                                       @RequestParam @Parameter(description = "Novo nome da disciplina" ) String name,
                                                       @RequestParam @Parameter(description = "Usuário que está editando a disciplina" ) User actor) {
        return ResponseEntity.ok(subjectService.editName
                (id, name, actor));
    }

    @Operation(summary = "Editando carga horária")
    @ApiResponse(responseCode = "200", description = "Subject edited successfully"
            , content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class),
            examples = @ExampleObject(value = "{\"nome\": \"Matematica\", \"workLoad\": 80}")))
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/{id}/workload")
    public ResponseEntity<SubjectResponseDTO> editWorkLoad(@PathVariable @Parameter(description = "ID da disciplina" ) Long id,
                                                           @RequestParam @Parameter(description = "Nova carga horária da disciplina" ) Integer workLoad,
                                                           @RequestParam @Parameter(description = "Usuário que está editando a disciplina" ) User actor) {
        return ResponseEntity.ok(subjectService.editWorkLoad
                (id, workLoad, actor));
    }

    @Operation(summary = "Encontre todos as disciplinas")
    @ApiResponse(responseCode = "200", description = "Disciplina encontrada com sucesso"
            , content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class),
            examples = @ExampleObject(value = "{\"nome\": \"Matematica\", \"workLoad\": 80}")))
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @GetMapping
    public ResponseEntity<Page<SubjectResponseDTO>> findSubjects(Pageable pageable) {
        return ResponseEntity.ok(subjectService.findSubjects
                (pageable));
    }


    @Operation(summary = "Disciplina encontrada por ID")
    @ApiResponse(responseCode = "200", description = "Disciplina encontrada com sucesso"
            , content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class),
            examples = @ExampleObject(value = "{\"nome\": \"Matematica\", \"workLoad\": 80}")))
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponseDTO> findSubjectById(@PathVariable @Parameter(description = "ID da disciplina" ) Long id) {
        return ResponseEntity.ok
                (subjectService.findSubjectById(id));
    }



    @Operation(summary = "Delete a disciplina")
    @ApiResponse(responseCode = "204", description = "Disciplina deletada com sucesso")
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "ID da disciplina" ) Long id, @RequestParam @Parameter(description = "Usuário que está deletando a disciplina" ) User actor) {
        subjectService.delete(id, actor);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Disciplinas filtradas")
    @ApiResponse(responseCode = "200", description = "Disciplinas filtradas com sucesso"
            , content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class),
            examples = @ExampleObject(value = "{\"name\": \"Matematica\", \"workLoad\": 80}")))
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/filter")
    public ResponseEntity<Page<SubjectResponseDTO>> subjectFilter(@RequestParam @Parameter(description = "Termo utilizado para busca" ) String termo,
                                                                  Pageable pageable) {
        return ResponseEntity.ok(subjectService.subjectFilter
                (termo, pageable));
    }

@Operation(summary = "Encontre disciplinas por professor")
    @ApiResponse(responseCode = "200", description = "Disciplinas por ID encontrados com sucesso")
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<Page<SubjectResponseDTO>> findSubjectsByTeacher(@PathVariable @Parameter(description = "ID do professor" ) Long teacherId,
                                                                          Pageable pageable) {
        return ResponseEntity.ok(subjectService.findSubjectsByTeacher
                (teacherId, pageable));
    }
}
