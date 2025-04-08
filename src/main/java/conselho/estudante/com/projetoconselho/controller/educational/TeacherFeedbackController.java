package conselho.estudante.com.projetoconselho.controller.educational;

import conselho.estudante.com.projetoconselho.models.dto.request.educational.TeacherFeedbackRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.educational.TeacherFeedbackResponseDTO;
import conselho.estudante.com.projetoconselho.services.educational.TeacherFeedbackService;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("educational/teacherfeedback")
@AllArgsConstructor
public class TeacherFeedbackController {

    private final TeacherFeedbackService service;

    @PostMapping
    public ResponseEntity<TeacherFeedbackResponseDTO> create(@RequestBody TeacherFeedbackRequestDTO requestDTO, @RequestParam User actor) {
        return ResponseEntity.ok(service.create(requestDTO, actor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherFeedbackResponseDTO> update(@PathVariable Long id, @RequestBody TeacherFeedbackRequestDTO requestDTO, @RequestParam User actor) {
        return ResponseEntity.ok(service.update(id, requestDTO, actor));
    }

    @PatchMapping("/{id}/edit-texts")
    public ResponseEntity<TeacherFeedbackResponseDTO> editTexts(@PathVariable Long id, @RequestParam(required = false) String strengths, @RequestParam(required = false) String weaknesses, @RequestParam(required = false) String suggestions, @RequestParam User actor) {
        return ResponseEntity.ok(service.editTexts(id, strengths, weaknesses, suggestions, actor));
    }

    @GetMapping
    public ResponseEntity<Page<TeacherFeedbackResponseDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/council/{councilId}")
    public ResponseEntity<Page<TeacherFeedbackResponseDTO>> findByCouncil(@PathVariable Long councilId, Pageable pageable) {
        return ResponseEntity.ok(service.findByCouncil(councilId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherFeedbackResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam User actor) {
        service.delete(id, actor);
        return ResponseEntity.noContent().build();
    }
}
