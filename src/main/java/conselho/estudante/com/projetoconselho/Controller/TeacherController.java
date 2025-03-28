package conselho.estudante.com.projetoconselho.Controller;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.USERS.TeacherRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS.TeacherResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
import conselho.estudante.com.projetoconselho.SERVICES.USERS.TeacherService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Classe de controller da entidade Teacher
 * autor Alex Zastrow
 */

@RestController
@RequestMapping("/teachers")
@Tag(name = "Teacher", description = "Recurso para gerenciamento de professores")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /*
     * Criar Teacher
     */
    @PostMapping
    public ResponseEntity<TeacherResponseDTO> criarTeacher(@RequestBody TeacherRequestDTO dto) {
        Teacher teacher = teacherService.toEntity(dto);
        Teacher savedTeacher = teacherService.criarTeacher(teacher);
        TeacherResponseDTO responseDTO = teacherService.toResponseDTO(savedTeacher);
        return ResponseEntity.ok(responseDTO);
    }

    /*
     * Atualizar Teacher
     */
    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> atualizarTeacher(@PathVariable Long id, @RequestBody TeacherRequestDTO dto) {
        Teacher teacher = teacherService.toEntity(dto);
        Teacher updatedTeacher = teacherService.atualizarTeacher(id, teacher);
        TeacherResponseDTO responseDTO = teacherService.toResponseDTO(updatedTeacher);
        return ResponseEntity.ok(responseDTO);
    }

    /*
     * Buscar Teacher
     */
    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> buscarPorId(@PathVariable Long id) {
        Teacher teacher = teacherService.buscarPorId(id);
        TeacherResponseDTO responseDTO = teacherService.toResponseDTO(teacher);
        return ResponseEntity.ok(responseDTO);
    }
}