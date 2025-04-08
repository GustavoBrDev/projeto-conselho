package conselho.estudante.com.projetoconselho.controller.users;

import conselho.estudante.com.projetoconselho.models.dto.request.users.TeacherRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.users.TeacherResponseDTO;
import conselho.estudante.com.projetoconselho.services.users.TeacherService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Classe de controller da entidade Teacher
 * autor Alex Zastrow
 */

@RestController
@RequestMapping("/users/teachers")
@Tag(name = "Teacher", description = "Recurso para gerenciamento de professores")
@AllArgsConstructor
public class TeacherController {


    private TeacherService teacherService;

    /*
     * Criar Teacher
     */
    @PostMapping
    public ResponseEntity<Void> criarTeacher(@RequestBody TeacherRequestDTO dto) {
        teacherService.create(dto);
        return ResponseEntity.ok().build();
    }

    /*
     * Atualizar Teacher
     */
    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> atualizarTeacher(@PathVariable Long id, @RequestBody TeacherRequestDTO dto) {
        /*Teacher teacher = teacherService.toEntity(dto);
        Teacher updatedTeacher = teacherService.atualizarTeacher(id, teacher);
        TeacherResponseDTO responseDTO = teacherService.toResponseDTO(updatedTeacher);
        return ResponseEntity.ok(responseDTO);*/
        return null;
    }

    /*
     * Buscar Teacher
     */
    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> buscarPorId(@PathVariable Long id) {
        /*Teacher teacher = teacherService.buscarPorId(id);
        TeacherResponseDTO responseDTO = teacherService.toResponseDTO(teacher);
        return ResponseEntity.ok(responseDTO);*/
        return null;
    }
}