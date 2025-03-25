package conselho.estudante.com.projetoconselho.Controller.USERS;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.USERS.StudentRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS.StudentResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Notification;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
import conselho.estudante.com.projetoconselho.SERVICES.USERS.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final AuthService authService;

    private User getAuthenticatedUser(Long actorId) {
        return authService.findUserById(actorId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário autenticado não encontrado."));
    }

    @Operation(summary = "Cria um estudante")
    @ApiResponse(responseCode = "201", description = "Estudante criado com sucesso")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody StudentRequestDTO studentRequestDTO, @RequestParam Long actorId) {
        try {
            User actor = getAuthenticatedUser(actorId);
            return ResponseEntity.status(HttpStatus.CREATED).body(studentService.create(studentRequestDTO, actor));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar estudante: " + e.getMessage());
        }
    }

    @Operation(summary = "Atualiza um estudante")
    @ApiResponse(responseCode = "200", description = "Estudante atualizado com sucesso")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody StudentRequestDTO studentRequestDTO, @RequestParam Long actorId) {
        try {
            User actor = getAuthenticatedUser(actorId);
            return ResponseEntity.ok(studentService.update(id, studentRequestDTO, actor));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar estudante: " + e.getMessage());
        }
    }

    @Operation(summary = "Busca um estudante por ID")
    @ApiResponse(responseCode = "200", description = "Estudante encontrado")
    @ApiResponse(responseCode = "404", description = "Estudante não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(studentService.findId(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estudante não encontrado: " + e.getMessage());
        }
    }

    @Operation(summary = "Lista todos os estudantes")
    @ApiResponse(responseCode = "200", description = "Lista de estudantes retornada com sucesso")
    @GetMapping
    public ResponseEntity<Page<StudentResponseDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(studentService.findStudents(pageable));
    }

    @Operation(summary = "Lista estudantes por classe")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @ApiResponse(responseCode = "404", description = "Classe não encontrada")
    @GetMapping("/by-class/{classId}")
    public ResponseEntity<?> findByClass(@PathVariable Long classId, Pageable pageable) {
        try {
            Classe classe = new Classe();
            classe.setId(classId);
            return ResponseEntity.ok(studentService.findStudentsClass(classe, pageable));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao buscar estudantes da classe: " + e.getMessage());
        }
    }

    @Operation(summary = "Edita o nome de um estudante")
    @ApiResponse(responseCode = "200", description = "Nome atualizado com sucesso")
    @PatchMapping("/{id}/name")
    public ResponseEntity<?> editName(@PathVariable Long id, @RequestParam String name, @RequestParam Long actorId) {
        try {
            User actor = getAuthenticatedUser(actorId);
            return ResponseEntity.ok(studentService.editName(id, name, actor));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao editar nome: " + e.getMessage());
        }
    }

    @Operation(summary = "Edita o email de um estudante")
    @ApiResponse(responseCode = "200", description = "Email atualizado com sucesso")
    @PatchMapping("/{id}/email")
    public ResponseEntity<?> editEmail(@PathVariable Long id, @RequestParam String email, @RequestParam Long actorId) {
        try {
            User actor = getAuthenticatedUser(actorId);
            return ResponseEntity.ok(studentService.editEmail(id, email, actor));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao editar e-mail: " + e.getMessage());
        }
    }

    @Operation(summary = "Edita a senha de um estudante")
    @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso")
    @PatchMapping("/{id}/password")
    public ResponseEntity<?> editPassword(@PathVariable Long id, @RequestParam String password, @RequestParam Long actorId) {
        try {
            User actor = getAuthenticatedUser(actorId);
            return ResponseEntity.ok(studentService.editPassword(id, password, actor));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao editar senha: " + e.getMessage());
        }
    }

    @Operation(summary = "Edita a imagem de um estudante")
    @ApiResponse(responseCode = "200", description = "Imagem atualizada com sucesso")
    @PatchMapping("/{id}/image")
    public ResponseEntity<?> editImage(@PathVariable Long id, @RequestParam String image, @RequestParam Long actorId) {
        try {
            User actor = getAuthenticatedUser(actorId);
            return ResponseEntity.ok(studentService.editImage(id, image, actor));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao editar imagem: " + e.getMessage());
        }
    }

    @Operation(summary = "Adiciona uma notificação para um estudante")
    @ApiResponse(responseCode = "200", description = "Notificação adicionada com sucesso")
    @PostMapping("/{id}/notifications")
    public ResponseEntity<?> addNotification(@PathVariable Long id, @RequestBody Notification notification) {
        try {
            return ResponseEntity.ok(studentService.addNotification(id, notification));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao adicionar notificação: " + e.getMessage());
        }
    }

    @Operation(summary = "Remove uma notificação de um estudante")
    @ApiResponse(responseCode = "200", description = "Notificação removida com sucesso")
    @DeleteMapping("/{id}/notifications")
    public ResponseEntity<?> removeNotification(@PathVariable Long id, @RequestBody Notification notification) {
        try {
            return ResponseEntity.ok(studentService.removeNotification(id, notification));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao remover notificação: " + e.getMessage());
        }
    }

    @Operation(summary = "Deleta um estudante")
    @ApiResponse(responseCode = "204", description = "Estudante removido com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @RequestParam Long actorId) {
        try {
            User actor = getAuthenticatedUser(actorId);
            studentService.delete(id, actor);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao excluir estudante: " + e.getMessage());
        }
    }
}
