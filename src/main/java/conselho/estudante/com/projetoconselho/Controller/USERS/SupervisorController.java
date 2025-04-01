package conselho.estudante.com.projetoconselho.Controller.USERS;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.USERS.SupervisorRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS.SupervisorResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Course;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Notification;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Supervisor;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
import conselho.estudante.com.projetoconselho.SERVICES.USERS.SupervisorService;
import io.swagger.v3.oas.annotations.Operation;
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

import java.util.List;

@RestController
@RequestMapping("/supervisores")
@AllArgsConstructor
@Tag(name = "Supervisor", description = "Recurso para gerenciamento de supervisores")
public class SupervisorController {

    private final SupervisorService supervisorService;

    // Criar supervisor
    @Operation(summary = "Cria um supervisor")
    @ApiResponse(responseCode = "201", description = "Supervisor criado com sucesso"
            , content = @Content(schema = @Schema(implementation = SupervisorResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Supervisor\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao criar supervisor")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PostMapping
    public SupervisorResponseDTO create(@RequestBody SupervisorRequestDTO supervisorRequestDTO, @RequestParam User actor) {
        return supervisorService.create(supervisorRequestDTO, actor);
    }

    // Atualizar supervisor
    @Operation(summary = "Atualiza um supervisor")
    @ApiResponse(responseCode = "200", description = "Supervisor atualizado com sucesso"
            , content = @Content(schema = @Schema(implementation = SupervisorResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Supervisor\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao atualizar supervisor")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PutMapping("/{id}")
    public SupervisorResponseDTO update(@PathVariable Long id, @RequestBody SupervisorRequestDTO supervisorRequestDTO, @RequestParam User actor) {
        return supervisorService.update(id, supervisorRequestDTO, actor);
    }

    // Editar nome de supervisor
    @Operation(summary = "Edita um supervisor")
    @ApiResponse(responseCode = "200", description = "Supervisor editado com sucesso"
            , content = @Content(schema = @Schema(implementation = SupervisorResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Supervisor\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar supervisor")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/{id}/name")
    public SupervisorResponseDTO editName(@PathVariable Long id, @RequestParam String name, @RequestParam User actor) {
        return supervisorService.editName(id, name, actor);
    }

    // Editar email de supervisor
    @Operation(summary = "Edita um supervisor")
    @ApiResponse(responseCode = "200", description = "Supervisor editado com sucesso"
            , content = @Content(schema = @Schema(implementation = SupervisorResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Supervisor\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar supervisor")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/{id}/email")
    public SupervisorResponseDTO editEmail(@PathVariable Long id, @RequestParam String email, @RequestParam User actor) {
        return supervisorService.editEmail(id, email, actor);
    }

    // Editar cadastro de supervisor
    @Operation(summary = "Edita um supervisor")
    @ApiResponse(responseCode = "200", description = "Supervisor editado com sucesso"
            , content = @Content(schema = @Schema(implementation = SupervisorResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Supervisor\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar supervisor")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/{id}/register")
    public SupervisorResponseDTO editRegister(@PathVariable Long id, @RequestParam Long register, @RequestParam User actor) {
        return supervisorService.editRegister(id, register, actor);
    }

    // Editar senha de supervisor
    @Operation(summary = "Edita um supervisor")
    @ApiResponse(responseCode = "200", description = "Supervisor editado com sucesso"
            , content = @Content(schema = @Schema(implementation = SupervisorResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Supervisor\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar supervisor")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/{id}/password")
    public SupervisorResponseDTO editPassword(@PathVariable Long id, @RequestParam String password, @RequestParam User actor) {
        return supervisorService.editPassword(id, password, actor);
    }

    // Editar imagem de perfil de supervisor
    @Operation(summary = "Edita um supervisor")
    @ApiResponse(responseCode = "200", description = "Supervisor editado com sucesso"
            , content = @Content(schema = @Schema(implementation = SupervisorResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Supervisor\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar supervisor")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/{id}/image")
    public SupervisorResponseDTO editImage(@PathVariable Long id, @RequestParam String image, @RequestParam User actor) {
        return supervisorService.editImage(id, image, actor);
    }

    // Buscar todos os supervisores com paginação
    @Operation(summary = "Busca todos os supervisores com paginação")
    @ApiResponse(responseCode = "200", description = "Supervisores encontrados com sucesso"
            , content = @Content(schema = @Schema(implementation = SupervisorResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Supervisor\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar supervisores")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @GetMapping
    public Page<SupervisorResponseDTO> findSupervisors(Pageable pageable) {
        return supervisorService.findSupervisors(pageable);
    }

    // Buscar supervisor por ID
    @Operation(summary = "Busca um supervisor por ID")
    @ApiResponse(responseCode = "200", description = "Supervisor encontrado com sucesso"
            , content = @Content(schema = @Schema(implementation = SupervisorResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Supervisor\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar supervisor")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/{id}")
    public SupervisorResponseDTO findById(@PathVariable Long id) {
        return supervisorService.findById(id);
    }

    // Buscar supervisor por email
    @Operation(summary = "Busca um supervisor por email")
    @ApiResponse(responseCode = "200", description = "Supervisor encontrado com sucesso"
            , content = @Content(schema = @Schema(implementation = SupervisorResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Supervisor\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar supervisor")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/email/{email}")
    public SupervisorResponseDTO findByEmail(@PathVariable String email) {
        return supervisorService.findByEmail(email);
    }

    // Adicionar notificação a supervisor
    @Operation(summary = "Adiciona uma notificação a um supervisor")
    @ApiResponse(responseCode = "200", description = "Notificação adicionada com sucesso"
            , content = @Content(schema = @Schema(implementation = SupervisorResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Supervisor\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao adicionar notificação")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PostMapping("/{id}/notifications")
    public SupervisorResponseDTO addNotification(@PathVariable Long id, @RequestBody Notification notification) {
        return supervisorService.addNotification(id, notification);
    }

    // Remover notificação de supervisor
    @Operation(summary = "Remove uma notificação de um supervisor")
    @ApiResponse(responseCode = "200", description = "Notificação removida com sucesso"
            , content = @Content(schema = @Schema(implementation = SupervisorResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Supervisor\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao remover notificação")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/{id}/notifications")
    public SupervisorResponseDTO removeNotification(@PathVariable Long id, @RequestBody Notification notification) {
        return supervisorService.removeNotification(id, notification);
    }

    // Deletar supervisor
    @Operation(summary = "Deleta um supervisor")
    @ApiResponse(responseCode = "204", description = "Supervisor deletado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao deletar supervisor")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @RequestParam User actor) {
        supervisorService.delete(id, actor);
    }

    // Adicionar curso a supervisor
    @Operation(summary = "Adiciona um curso a um supervisor")
    @ApiResponse(responseCode = "200", description = "Curso adicionado com sucesso"
            , content = @Content(schema = @Schema(implementation = SupervisorResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Supervisor\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao adicionar curso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PostMapping("/{id}/courses")
    public SupervisorResponseDTO addCourse(@PathVariable Long id, @RequestBody Course course, @RequestParam User actor) {
        return supervisorService.addCourse(id, course, actor);
    }

    // Remover curso de supervisor
    @Operation(summary = "Remove um curso de um supervisor")
    @ApiResponse(responseCode = "200", description = "Curso removido com sucesso"
            , content = @Content(schema = @Schema(implementation = SupervisorResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Supervisor\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao remover curso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/{id}/courses")
    public SupervisorResponseDTO removeCourse(@PathVariable Long id, @RequestBody Course course, @RequestParam User actor) {
        return supervisorService.removeCourse(id, course, actor);
    }

    // Filtrar supervisores por curso
    @Operation(summary = "Filtrar supervisores por curso")
    @ApiResponse(responseCode = "200", description = "Supervisores filtrados com sucesso"
            , content = @Content(schema = @Schema(implementation = SupervisorResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Supervisor\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao filtrar supervisores")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/course/{courseId}")
    public Page<SupervisorResponseDTO> filterByCourse(@PathVariable Long courseId, Pageable pageable) {
        return supervisorService.filterByCourse(courseId, pageable);
    }

    // Filtrar supervisores por turma
    @Operation(summary = "Filtrar supervisores por turma")
    @ApiResponse(responseCode = "200", description = "Supervisores filtrados com sucesso"
            , content = @Content(schema = @Schema(implementation = SupervisorResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Supervisor\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao filtrar supervisores")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/class/{classId}")
    public Page<SupervisorResponseDTO> filterByClass(@PathVariable Long classId, Pageable pageable) {
        return supervisorService.filterByClass(classId, pageable);
    }

    // Pesquisa inteligente de supervisores
    @Operation(summary = "Pesquisa inteligente de supervisores")
    @ApiResponse(responseCode = "200", description = "Supervisores encontrados com sucesso"
            , content = @Content(schema = @Schema(implementation = SupervisorResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Supervisor\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao pesquisar supervisores")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/search")
    public Page<SupervisorResponseDTO> intelligentSearch(@RequestParam String searchTerm, Pageable pageable) {
        return supervisorService.intelligentSearch(searchTerm, pageable);
    }
}
