package conselho.estudante.com.projetoconselho.Controller.USERS;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.USERS.StudentRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS.StudentResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Notification;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Student;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.DadosDuplicadosException;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.SERVICES.USERS.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


/**
 * Controller para os métodos relacionados ao gerenciamento de estudantes.
 * @author Camilly Chelest
 * @since 26/03/2025
 */
@RestController
@RequestMapping("/api/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // Cria um novo estudante
    @Tag(name = "Estudantes")
    @Operation(summary = "Cria um novo estudante")
    @ApiResponse(responseCode = "201", description = "Estudante criado com sucesso"
            , content = @Content(schema = @Schema(implementation = StudentResponseDTO.class),
        examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Gustavo Stinghen\", \"registration\": 123456, \"email\": \"7G9Gt@example.com\", \"password\": \"123456\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao criar estudante")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PostMapping
    public StudentResponseDTO createStudent(@RequestBody @Valid @Parameter(description = "Dados do estudante a ser criado") StudentRequestDTO studentRequestDTO,
                                            @RequestParam @Parameter(description = "Usuário que está criando o estudante") User actor) {
        return studentService.create(studentRequestDTO, actor);
    }

    // Atualiza um estudante existente
    @Tag(name = "Estudantes")
    @Operation(summary = "Atualiza um estudante existente")
    @ApiResponse(responseCode = "200", description = "Estudante atualizado com sucesso"
            , content = @Content(schema = @Schema(implementation = StudentResponseDTO.class),
              examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Gustavo Stinghen\", \"registration\": 123456, \"email\": \"7G9Gt@example.com\", \"password\": \"123456\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao atualizar estudante")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PutMapping("/{id}")
    public StudentResponseDTO updateStudent(@PathVariable @Parameter(description = "ID do estudante a ser atualizado") Long id,
                                            @RequestBody @Valid @Parameter(description = "Novos dados do estudante") StudentRequestDTO studentRequestDTO,
                                            @RequestParam @Parameter(description = "Usuário que está atualizando o estudante") User actor) {
        return studentService.update(id, studentRequestDTO, actor);
    }

    // Edita o nome do estudante
    @Tag(name = "Estudantes")
    @Operation(summary = "Edita o nome do estudante")
    @ApiResponse(responseCode = "200", description = "Nome do estudante editado com sucesso"
            , content = @Content(schema = @Schema(implementation = StudentResponseDTO.class),
              examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Gustavo Stinghen\", \"registration\": 123456, \"email\": \"7G9Gt@example.com\", \"password\": \"123456\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar nome do estudante")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/{id}/name")
    public StudentResponseDTO editName(@PathVariable Long id, @RequestParam String name, @RequestParam User actor) {
        return studentService.editName(id, name, actor);
    }

    // Edita o email do estudante
    @Tag(name = "Estudantes")
    @Operation(summary = "Edita o email do estudante")
    @ApiResponse(responseCode = "200", description = "Email do estudante editado com sucesso"
            , content = @Content(schema = @Schema(implementation = StudentResponseDTO.class),
              examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Gustavo Stinghen\", \"registration\": 123456, \"email\": \"7G9Gt@example.com\", \"password\": \"123456\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar email do estudante")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/{id}/email")
    public StudentResponseDTO editEmail(@PathVariable Long id, @RequestParam String email, @RequestParam User actor) {
        return studentService.editEmail(id, email, actor);
    }

    // Edita a matrícula do estudante
    @Tag(name = "Estudantes")
    @Operation(summary = "Edita a matrícula do estudante")
    @ApiResponse(responseCode = "200", description = "Matrícula do estudante editada com sucesso"
            , content = @Content(schema = @Schema(implementation = StudentResponseDTO.class),
              examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Gustavo Stinghen\", \"registration\": 123456, \"email\": \"7G9Gt@example.com\", \"password\": \"123456\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar matrícula do estudante")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/{id}/registration")
    public StudentResponseDTO editRegistration(@PathVariable Long id, @RequestParam Long registration, @RequestParam User actor) {
        return studentService.editRegistration(id, registration, actor);
    }

    // Edita a senha do estudante
    @Tag(name = "Estudantes")
    @Operation(summary = "Edita a senha do estudante")
    @ApiResponse(responseCode = "200", description = "Senha do estudante editada com sucesso"
            , content = @Content(schema = @Schema(implementation = StudentResponseDTO.class),
              examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Gustavo Stinghen\", \"registration\": 123456, \"email\": \"7G9Gt@example.com\", \"password\": \"123456\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar senha do estudante")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/{id}/password")
    public StudentResponseDTO editPassword(@PathVariable Long id, @RequestParam String password, @RequestParam User actor) {
        return studentService.editPassword(id, password, actor);
    }

    // Edita a imagem do estudante
    @Tag(name = "Estudantes")
    @Operation(summary = "Edita a imagem do estudante")
    @ApiResponse(responseCode = "200", description = "Imagem do estudante editada com sucesso"
            , content = @Content(schema = @Schema(implementation = StudentResponseDTO.class),
              examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Gustavo Stinghen\", \"registration\": 123456, \"email\": \"7G9Gt@example.com\", \"password\": \"123456\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar imagem do estudante")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/{id}/image")
    public StudentResponseDTO editImage(@PathVariable Long id, @RequestParam String image, @RequestParam User actor) {
        return studentService.editImage(id, image, actor);
    }

    // Busca todos os estudantes com paginação
    @Tag(name = "Estudantes")
    @Operation(summary = "Busca todos os estudantes com paginação")
    @ApiResponse(responseCode = "200", description = "Estudantes encontrados com sucesso"
            , content = @Content(schema = @Schema(implementation = StudentResponseDTO.class),
              examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Gustavo Stinghen\", \"registration\": 123456, \"email\": \"7G9Gt@example.com\", \"password\": \"123456\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar estudantes")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @GetMapping
    public Page<StudentResponseDTO> findStudents(Pageable pageable) {
        return studentService.findStudents(pageable);
    }

    // Busca estudantes de uma determinada classe
    @Tag(name = "Estudantes")
    @Operation(summary = "Busca estudantes de uma determinada classe")
    @ApiResponse(responseCode = "200", description = "Estudantes encontrados com sucesso"
            , content = @Content(schema = @Schema(implementation = StudentResponseDTO.class),
              examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Gustavo Stinghen\", \"registration\": 123456, \"email\": \"7G9Gt@example.com\", \"password\": \"123456\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar estudantes")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/class/{classeId}")
    public Page<StudentResponseDTO> findStudentsByClass(@PathVariable Long classeId, Pageable pageable) {
        Classe classe = new Classe();
        classe.setId(classeId); // Supondo que você tenha uma maneira de pegar a classe por ID
        return studentService.findStudentsClass(classe, pageable);
    }

    // Busca um estudante pelo ID
    @Tag(name = "Estudantes")
    @Operation(summary = "Busca um estudante pelo ID")
    @ApiResponse(responseCode = "200", description = "Estudante encontrado com sucesso"
            , content = @Content(schema = @Schema(implementation = StudentResponseDTO.class),
              examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Gustavo Stinghen\", \"registration\": 123456, \"email\": \"7G9Gt@example.com\", \"password\": \"123456\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar estudante")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/{id}")
    public StudentResponseDTO findStudentById(@PathVariable Long id) {
        return studentService.findId(id);
    }

    // Busca um estudante pelo email
    @Tag(name = "Estudantes")
    @Operation(summary = "Busca um estudante pelo email")
    @ApiResponse(responseCode = "200", description = "Estudante encontrado com sucesso"
            , content = @Content(schema = @Schema(implementation = StudentResponseDTO.class),
              examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Gustavo Stinghen\", \"registration\": 123456, \"email\": \"7G9Gt@example.com\", \"password\": \"123456\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar estudante")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/email/{email}")
    public StudentResponseDTO findStudentByEmail(@PathVariable String email) {
        return studentService.findByEmail(email);
    }

    // Deleta um estudante
    @Tag(name = "Estudantes")
    @Operation(summary = "Deleta um estudante")
    @ApiResponse(responseCode = "204", description = "Estudante deletado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao deletar estudante")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Long id, @RequestParam User actor) {
        studentService.delete(id, actor);
    }

    // Adiciona um estudante a uma classe
    @Tag(name = "Estudantes")
    @Operation(summary = "Adiciona um estudante a uma classe")
    @ApiResponse(responseCode = "200", description = "Estudante adicionado à classe com sucesso"
            , content = @Content(schema = @Schema(implementation = StudentResponseDTO.class),
              examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Gustavo Stinghen\", \"registration\": 123456, \"email\": \"7G9Gt@example.com\", \"password\": \"123456\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao adicionar estudante à classe")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PostMapping("/{studentId}/class/{classeId}")
    public StudentResponseDTO addStudentToClass(@PathVariable Long studentId, @PathVariable Long classeId, @RequestParam User actor) {
        Student student = studentService.findId(studentId).convert();
        Classe classe = new Classe();
        classe.setId(classeId);
        return studentService.addStudentClass(student, classe, actor);
    }

    // Remove um estudante de uma classe
    @Tag(name = "Estudantes")
    @Operation(summary = "Remove um estudante de uma classe")
    @ApiResponse(responseCode = "200", description = "Estudante removido da classe com sucesso"
            , content = @Content(schema = @Schema(implementation = StudentResponseDTO.class),
              examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Gustavo Stinghen\", \"registration\": 123456, \"email\": \"7G9Gt@example.com\", \"password\": \"123456\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao remover estudante da classe")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/{studentId}/class/{classeId}")
    public StudentResponseDTO removeStudentFromClass(@PathVariable Long studentId, @PathVariable Long classeId, @RequestParam User actor) {
        Student student = studentService.findId(studentId).convert();
        Classe classe = new Classe();
        classe.setId(classeId);
        return studentService.removeStudentClass(student, classe, actor);
    }

    // Adiciona uma notificação a um estudante
    @Tag(name = "Estudantes")
    @Operation(summary = "Adiciona uma notificação a um estudante")
    @ApiResponse(responseCode = "200", description = "Notificação adicionada ao estudante com sucesso"
            , content = @Content(schema = @Schema(implementation = StudentResponseDTO.class),
              examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Gustavo Stinghen\", \"registration\": 123456, \"email\": \"7G9Gt@example.com\", \"password\": \"123456\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao adicionar notificação ao estudante")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PostMapping("/{id}/notification")
    public StudentResponseDTO addNotification(@PathVariable Long id, @RequestBody Notification notification) {
        return studentService.addNotification(id, notification);
    }

    // Remove uma notificação de um estudante
    @Tag(name = "Estudantes")
    @Operation(summary = "Remove uma notificação de um estudante")
    @ApiResponse(responseCode = "200", description = "Notificação removida do estudante com sucesso"
            , content = @Content(schema = @Schema(implementation = StudentResponseDTO.class),
              examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Gustavo Stinghen\", \"registration\": 123456, \"email\": \"7G9Gt@example.com\", \"password\": \"123456\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao remover notificação do estudante")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/{id}/notification")
    public StudentResponseDTO removeNotification(@PathVariable Long id, @RequestBody Notification notification) {
        return studentService.removeNotification(id, notification);
    }

    // Tratar exceções globalmente
    @Tag(name = "Estudantes")
    @Operation(summary = "Tratar exceções globalmente")
    @ApiResponse(responseCode = "400", description = "Erro ao adicionar estudante à classe")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")

    @ExceptionHandler(DadosDuplicadosException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleDadosDuplicadosException(DadosDuplicadosException e) {
        return e.getMessage();
    }

    @ExceptionHandler(NaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNaoEncontradoException(NaoEncontradoException e) {
        return e.getMessage();
    }
}
