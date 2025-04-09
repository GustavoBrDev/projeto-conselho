package conselho.estudante.com.projetoconselho.controller.administration;

import conselho.estudante.com.projetoconselho.models.dto.request.administration.ClasseRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.administration.ClasseResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.administration.Classe;
import conselho.estudante.com.projetoconselho.models.entity.administration.Course;
import conselho.estudante.com.projetoconselho.models.entity.users.Student;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import conselho.estudante.com.projetoconselho.services.administration.ClasseService;
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
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para gerenciar as operações relacionadas a Classe.
 * Realiza a exposição das operações da ClasseService via API REST.
 *
 * @author Camilly
 * @since 01/04/2025
 */
@RestController
@RequestMapping("/administration/classes")
@Tag(name = "Classe", description = "Controlador para gerenciar as operações relacionadas a Classe")
@AllArgsConstructor
public class ClasseController {

    private ClasseService classeService;

    @Operation(summary = "Cria uma nova Classe")
    @ApiResponse(responseCode = "201", description = "Classe criada com sucesso"
            , content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClasseResponseDTO.class),
             examples = @ExampleObject(ref = "classeResponseDTO")))
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @PostMapping
    public ClasseResponseDTO create(@RequestBody @Parameter(description = "Dados da Classe") ClasseRequestDTO classeRequestDTO,
                                    @RequestHeader("user") @Parameter(description = "Usuário logado") User actor) {
        return classeService.create(classeRequestDTO, actor);
    }

    @Operation(summary = "Atualiza uma Classe existente")
    @ApiResponse(responseCode = "200", description = "Classe atualizada com sucesso"
            , content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClasseResponseDTO.class),
             examples = @ExampleObject(ref = "classeResponseDTO")))
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @PutMapping("/{id}")
    public ClasseResponseDTO update(@PathVariable @Parameter(description = "ID da Classe") Long id,
                                    @RequestBody @Parameter(description = "Dados da Classe") ClasseRequestDTO classeRequestDTO,
                                    @RequestHeader("user") @Parameter(description = "Usuário logado") User actor) {
        return classeService.update(id, classeRequestDTO, actor);
    }

    @Operation(summary = "Edita uma Classe existente")
    @ApiResponse(responseCode = "200", description = "Classe editada com sucesso"
            , content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClasseResponseDTO.class),
             examples = @ExampleObject(ref = "classeResponseDTO")))
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/editName/{id}")
    public ClasseResponseDTO editName(@PathVariable @Parameter(description = "ID da Classe") Long id,
                                      @RequestParam @Parameter(description = "Nome da Classe") String name,
                                      @RequestHeader("user") @Parameter(description = "Usuário logado") User actor) {
        return classeService.editName(id, name, actor);
    }

    @Operation(summary = "Edita a sigla de uma Classe existente")
    @ApiResponse(responseCode = "200", description = "Classe editada com sucesso"
            , content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClasseResponseDTO.class),
             examples = @ExampleObject(ref = "classeResponseDTO")))
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/editAcronym/{id}")
    public ClasseResponseDTO editAcronym(@PathVariable @Parameter(description = "ID da Classe") Long id,
                                         @RequestParam @Parameter(description = "Sigla da Classe") String acronym,
                                         @RequestHeader("user") @Parameter(description = "Usuário logado") User actor) {
        return classeService.editAcronym(id, acronym, actor);
    }

    @Operation(summary = "Edita o curso de uma Classe existente")
    @ApiResponse(responseCode = "200", description = "Classe editada com sucesso"
            , content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClasseResponseDTO.class),
             examples = @ExampleObject(ref = "classeResponseDTO")))
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/editCourse/{id}")
    public ClasseResponseDTO editCourse(@PathVariable @Parameter(description = "ID da Classe") Long id,
                                        @RequestBody @Parameter(description = "Curso da Classe") Course course,
                                        @RequestHeader("user") @Parameter(description = "Usuário logado") User actor) {
        return classeService.editCourse(id, course, actor);
    }

    @Operation(summary = "Edita o estado ativo de uma Classe existente")
    @ApiResponse(responseCode = "200", description = "Classe editada com sucesso"
            , content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClasseResponseDTO.class),
             examples = @ExampleObject(ref = "classeResponseDTO")))
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/editActive/{id}")
    public ClasseResponseDTO editActive(@PathVariable @Parameter(description = "ID da Classe") Long id,
                                        @RequestParam @Parameter(description = "Estado ativo da Classe") boolean active,
                                        @RequestHeader("user") @Parameter(description = "Usuário logado") User actor) {
        return classeService.editActive(id, active, actor);
    }

    @Operation(summary = "Busca todas as Classes existentes")
    @ApiResponse(responseCode = "200", description = "Classes encontradas com sucesso"
            , content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClasseResponseDTO.class),
             examples = @ExampleObject(ref = "classeResponseDTO")))
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @GetMapping
    public Page<ClasseResponseDTO> findClasses(Pageable pageable) {
        return classeService.findClasses(pageable);
    }

    @Operation(summary = "Busca uma Classe existente pelo ID")
    @ApiResponse(responseCode = "200", description = "Classe encontrada com sucesso"
            , content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClasseResponseDTO.class),
             examples = @ExampleObject(ref = "classeResponseDTO")))
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/{id}")
    public ClasseResponseDTO findById(@PathVariable @Parameter(description = "ID da Classe" ) Long id) {
        return classeService.findById(id);
    }

    @Operation(summary = "Adiciona um estudante a uma Classe existente")
    @ApiResponse(responseCode = "200", description = "Estudante adicionado com sucesso")
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/addStudent/{id}")
    public void addStudentToClasse(@PathVariable @Parameter(description = "ID da Classe" ) Long id,
                                   @RequestBody @Parameter(description = "Estudante a ser adicionado") Student student,
                                   @RequestHeader("user") @Parameter(description = "Usuário logado") User actor) {
        Classe classe = classeService.findObjectClasse(id);
        classeService.addStudentToClasse(classe, student, actor);
    }

    @Operation(summary = "Remove um estudante de uma Classe existente")
    @ApiResponse(responseCode = "200", description = "Estudante removido com sucesso")
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/removeStudent/{id}")
    public void removeStudentFromClasse(@PathVariable @Parameter(description = "ID da Classe" ) Long id,
                                        @RequestBody @Parameter(description = "Estudante a ser removido") Student student,
                                        @RequestHeader("user") @Parameter(description = "Usuário logado") User actor) {
        Classe classe = classeService.findObjectClasse(id);
        classeService.removeStudentFromClasse(classe, student, actor);
    }

    @Operation(summary = "Remove uma Classe existente")
    @ApiResponse(responseCode = "200", description = "Classe removida com sucesso")
    @ApiResponse(responseCode = "400", description = "Pedido ruim")
    @ApiResponse(responseCode = "500", description = "Erro do Servidor Interno")
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Parameter(description = "ID da Classe" ) Long id,
                       @RequestHeader("user") @Parameter(description = "Usuário logado") User actor) {
        classeService.delete(id, actor);
    }
}
