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

    @Tag(name = "Subject")
    @Operation(summary = "Create a subject")
    @ApiResponse(responseCode = "201", description = "Subject created successfully"
            , content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class))
             examples = @ExampleObject(value = "{\"name\": \"Matematica\", \"workLoad\": 80}"))
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @SecurityRequirement(name = "Bearer")
    @PostMapping
    public ResponseEntity<SubjectResponseDTO> create(@RequestBody SubjectRequestDTO subjectRequestDTO, @RequestParam User actor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectService.create
                (subjectRequestDTO, actor));
    }

    @Tag(name = "Subject")
    @Operation(summary = "Update a subject")
    @ApiResponse(responseCode = "200", description = "Subject updated successfully"
            , content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class))
            examples = @ExampleObject(value = "{\"name\": \"Matematica\", \"workLoad\": 80}"))
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @SecurityRequirement(name = "Bearer")
    @PutMapping("/{id}")
    public ResponseEntity<SubjectResponseDTO> update(@PathVariable Long id, @RequestBody SubjectRequestDTO subjectRequestDTO, @RequestParam User actor) {
        return ResponseEntity.ok(subjectService.update
                (id, subjectRequestDTO, actor));
    }

    @Tag(name = "Subject")
    @Operation(summary = "Edit a subject")
    @ApiResponse(responseCode = "200", description = "Subject edited successfully"
            , content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class))
            examples = @ExampleObject(value = "{\"name\": \"Matematica\", \"workLoad\": 80}"))
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/{id}/name")
    public ResponseEntity<SubjectResponseDTO> editName(@PathVariable Long id, @RequestParam String name, @RequestParam User actor) {
        return ResponseEntity.ok(subjectService.editName
                (id, name, actor));
    }

    @Tag(name = "Subject")
    @Operation(summary = "Edit a subject")
    @ApiResponse(responseCode = "200", description = "Subject edited successfully"
            , content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class))
            examples = @ExampleObject(value = "{\"name\": \"Matematica\", \"workLoad\": 80}"))
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/{id}/workload")
    public ResponseEntity<SubjectResponseDTO> editWorkLoad(@PathVariable Long id, @RequestParam Integer workLoad, @RequestParam User actor) {
        return ResponseEntity.ok(subjectService.editWorkLoad
                (id, workLoad, actor));
    }

    @Tag(name = "Subject")
    @Operation(summary = "Find all subjects")
    @ApiResponse(responseCode = "200", description = "Subjects found successfully"
            , content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class))
            examples = @ExampleObject(value = "{\"name\": \"Matematica\", \"workLoad\": 80}"))
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @SecurityRequirement(name = "Bearer")
    @GetMapping
    public ResponseEntity<Page<SubjectResponseDTO>> findSubjects(Pageable pageable) {
        return ResponseEntity.ok(subjectService.findSubjects
                (pageable));
    }

    @Tag(name = "Subject")
    @Operation(summary = "Find a subject by id")
    @ApiResponse(responseCode = "200", description = "Subject found successfully"
            , content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class))
            examples = @ExampleObject(value = "{\"name\": \"Matematica\", \"workLoad\": 80}"))
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponseDTO> findSubjectById(@PathVariable Long id) {
        return ResponseEntity.ok
                (subjectService.findSubjectById(id));
    }

    @Tag(name = "Subject")
    @Operation(summary = "Find a subject by id")
    @ApiResponse(responseCode = "200", description = "Subject found successfully"
            , content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class))
            examples = @ExampleObject(value = "{\"name\": \"Matematica\", \"workLoad\": 80}"))
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/{id}/object")
    public ResponseEntity<Subject> getObjectSubject(@PathVariable Long id) {
        return ResponseEntity.ok
                (subjectService.getObjectSubject(id));
    }

    @Tag(name = "Subject")
    @Operation(summary = "Delete a subject")
    @ApiResponse(responseCode = "204", description = "Subject deleted successfully")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subjectService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Tag(name = "Subject")
    @Operation(summary = "Filter subjects")
    @ApiResponse(responseCode = "200", description = "Subjects filtered successfully"
            , content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class))
            examples = @ExampleObject(value = "{\"name\": \"Matematica\", \"workLoad\": 80}"))
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/filter")
    public ResponseEntity<Page<SubjectResponseDTO>> subjectFilter(@RequestParam String termo, Pageable pageable) {
        return ResponseEntity.ok(subjectService.subjectFilter
                (termo, pageable));
    }

    @Tag(name = "Subject")
    @Operation(summary = "Find subjects by teacher")
    @ApiResponse(responseCode = "200", description = "Subjects found successfully"
            , content = @Content(schema = @Schema(implementation = SubjectResponseDTO.class))
            examples = @ExampleObject(value = "{\"name\": \"Matematica\", \"workLoad\": 80}"))
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<Page<SubjectResponseDTO>> findSubjectsByTeacher(@PathVariable Long teacherId, Pageable pageable) {
        return ResponseEntity.ok(subjectService.findSubjectsByTeacher
                (teacherId, pageable));
    }
}
