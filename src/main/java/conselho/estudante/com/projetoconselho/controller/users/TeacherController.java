package conselho.estudante.com.projetoconselho.controller.users;

import conselho.estudante.com.projetoconselho.models.dto.request.users.TeacherRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.users.TeacherResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.administration.Course;
import conselho.estudante.com.projetoconselho.models.entity.administration.Notification;
import conselho.estudante.com.projetoconselho.models.entity.users.Admin;
import conselho.estudante.com.projetoconselho.models.entity.users.Teacher;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import conselho.estudante.com.projetoconselho.services.users.TeacherService;
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

@RestController
@RequestMapping("/users/teachers")
@AllArgsConstructor
@Tag(name = "Teacher", description = "Recurso para gerenciamento de teacheres")
public class TeacherController {

    private final TeacherService teacherService;

    // Criar teacher
    @Operation(summary = "Cria um teacher")
    @ApiResponse(responseCode = "201", description = "Teacher criado com sucesso"
            , content = @Content(schema = @Schema(implementation = TeacherResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Teacher\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao criar teacher")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PostMapping
    public TeacherResponseDTO create(@RequestBody TeacherRequestDTO teacherRequestDTO/*,@RequestParam User actor*/) {

        Admin actor = Admin.builder()
                .id(1L)
                .username("adminTrabalhandoCom@Senai")
                .password("adminConselho@estudante.com")
                .build();

        return teacherService.create(teacherRequestDTO, actor);
    }

    // Atualizar teacher
    @Operation(summary = "Atualiza um professor")
    @ApiResponse(responseCode = "200", description = "Teacher atualizado com sucesso"
            , content = @Content(schema = @Schema(implementation = TeacherResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Teacher\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao atualizar teacher")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PutMapping("/{id}")
    public TeacherResponseDTO update(@PathVariable Long id, @RequestBody TeacherRequestDTO teacherRequestDTO/*, @RequestParam User actor*/) {

        Admin actor = Admin.builder()
                .id(1L)
                .username("adminTrabalhandoCom@Senai")
                .password("adminConselho@estudante.com")
                .build();

        return teacherService.update(id, teacherRequestDTO, actor);
    }

    // Editar nome de teacher
    @Operation(summary = "Edita o nome de um professor")
    @ApiResponse(responseCode = "200", description = "Teacher editado com sucesso"
            , content = @Content(schema = @Schema(implementation = TeacherResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Teacher\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar teacher")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/editName/{id}")
    public TeacherResponseDTO editName(@PathVariable Long id, @RequestParam String name/*, @RequestParam User actor*/) {

        Admin actor = Admin.builder()
                .id(1L)
                .username("adminTrabalhandoCom@Senai")
                .password("adminConselho@estudante.com")
                .build();


        return teacherService.editName(id, name, actor);
    }

    // Editar email de teacher
    @Operation(summary = "Edita o email de um professor")
    @ApiResponse(responseCode = "200", description = "Teacher editado com sucesso"
            , content = @Content(schema = @Schema(implementation = TeacherResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Teacher\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar teacher")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/editEmail/{id}")
    public TeacherResponseDTO editEmail(@PathVariable Long id, @RequestParam String email/*, @RequestParam User actor*/) {

        Admin actor = Admin.builder()
                .id(1L)
                .username("adminTrabalhandoCom@Senai")
                .password("adminConselho@estudante.com")
                .build();

        return teacherService.editEmail(id, email, actor);
    }

    // Editar cadastro de teacher
    @Operation(summary = "Edita o cadastro de um professor")
    @ApiResponse(responseCode = "200", description = "Teacher editado com sucesso"
            , content = @Content(schema = @Schema(implementation = TeacherResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Teacher\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar teacher")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/editRegister/{id}")
    public TeacherResponseDTO editRegister(@PathVariable Long id, @RequestParam Long register/*, @RequestParam User actor*/) {

        Admin actor = Admin.builder()
                .id(1L)
                .username("adminTrabalhandoCom@Senai")
                .password("adminConselho@estudante.com")
                .build();


        return teacherService.editRegister(id, register, actor);
    }

    // Editar senha de teacher
    @Operation(summary = "Edita a senha de um professor")
    @ApiResponse(responseCode = "200", description = "Teacher editado com sucesso"
            , content = @Content(schema = @Schema(implementation = TeacherResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Teacher\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar teacher")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/editPassword/{id}")
    public TeacherResponseDTO editPassword(@PathVariable Long id, @RequestParam String password/*, @RequestParam User actor*/) {

        Admin actor = Admin.builder()
                .id(1L)
                .username("adminTrabalhandoCom@Senai")
                .password("adminConselho@estudante.com")
                .build();


        return teacherService.editPassword(id, password, actor);
    }

    @Operation(summary = "Edita a imagem de perfil de um professor")
    @ApiResponse(responseCode = "200", description = "Teacher editado com sucesso"
            , content = @Content(schema = @Schema(implementation = TeacherResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Teacher\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar teacher")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/editImage/{id}")
    public TeacherResponseDTO editImage(@PathVariable Long id, @RequestParam String image/*, @RequestParam User actor*/) {

        Admin actor = Admin.builder()
                .id(1L)
                .username("adminTrabalhandoCom@Senai")
                .password("adminConselho@estudante.com")
                .build();

        return teacherService.editImage(id, image, actor);
    }

    // Buscar todos os teacheres com paginação
    @Operation(summary = "Busca todos os professores com paginação")
    @ApiResponse(responseCode = "200", description = "Teacheres encontrados com sucesso"
            , content = @Content(schema = @Schema(implementation = TeacherResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Teacher\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar teacheres")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @GetMapping
    public Page<TeacherResponseDTO> findTeachers( @Parameter(description = "Objeto Pageable com informações de paginação") Pageable pageable) {
        return teacherService.findAll(pageable);
    }

    // Buscar teacher por ID
    @Operation(summary = "Busca um professor por ID")
    @ApiResponse(responseCode = "200", description = "Teacher encontrado com sucesso"
            , content = @Content(schema = @Schema(implementation = TeacherResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Teacher\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar teacher")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/{id}")
    public TeacherResponseDTO findById(@PathVariable Long id) {
        return teacherService.findById(id);
    }


    // Adicionar notificação a teacher
    @Operation(summary = "Adiciona uma notificação a um professor")
    @ApiResponse(responseCode = "200", description = "Notificação adicionada com sucesso"
            , content = @Content(schema = @Schema(implementation = TeacherResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Teacher\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao adicionar notificação")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/addNotification/{id}")
    public TeacherResponseDTO addNotification(@PathVariable Long id, @RequestBody Notification notification) {
        return teacherService.addNotification(id, notification);
    }

    // Remover notificação de teacher
    @Operation(summary = "Remove uma notificação de um teacher")
    @ApiResponse(responseCode = "200", description = "Notificação removida com sucesso"
            , content = @Content(schema = @Schema(implementation = TeacherResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Teacher\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao remover notificação")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/removeNotification/{id}")
    public TeacherResponseDTO removeNotification(@PathVariable Long id, @RequestBody Notification notification) {
        return teacherService.removeNotification(id, notification);
    }

    // Deletar teacher
    @Operation(summary = "Deleta um professor")
    @ApiResponse(responseCode = "204", description = "Teacher deletado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao deletar teacher")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id/*, @RequestParam User actor*/) {

        Admin actor = Admin.builder()
                .id(1L)
                .username("adminTrabalhandoCom@Senai")
                .password("adminConselho@estudante.com")
                .build();

        teacherService.delete(id, actor);
    }

    // Adicionar curso a teacher
    @Operation(summary = "Adiciona um curso a um professor")
    @ApiResponse(responseCode = "200", description = "Curso adicionado com sucesso"
            , content = @Content(schema = @Schema(implementation = TeacherResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Teacher\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao adicionar curso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/addCourse/{id}")
    public TeacherResponseDTO addCourse(@PathVariable Long id, @RequestBody Course course, @RequestParam User actor) {
        return teacherService.addCourse(id, course, actor);
    }

    @Operation(summary = "Remove um curso de um professor")
    @ApiResponse(responseCode = "200", description = "Curso removido com sucesso"
            , content = @Content(schema = @Schema(implementation = TeacherResponseDTO.class),
            examples = @ExampleObject("{\"name\": \"Teacher\", \"email\": \"t2YJi@example.com\", \"register\": 12345678, \"password\": \"senha123\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao remover curso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/removeCourse/{id}")
    public TeacherResponseDTO removeCourse(@PathVariable Long id, @RequestBody Course course, @RequestParam User actor) {
        return teacherService.removeCourse(id, course, actor);
    }



}
