package conselho.estudante.com.projetoconselho.Controller.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.EDUCATIONAL.ClassFeedbackRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL.ClassFeedbackResponseDTO;
import conselho.estudante.com.projetoconselho.SERVICES.EDUCATIONAL.ClassFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador responsável pelas operações relacionadas a feedbacks de turma.
 * Fornece endpoints para criação, atualização, recuperação e exclusão de feedbacks.
 */

@RestController
@RequestMapping("/api/class-feedbacks")
@RequiredArgsConstructor
public class ClassFeedbackController {

    private final ClassFeedbackService service;

    @PostMapping
    public ResponseEntity<ClassFeedbackResponseDTO> create(@RequestBody ClassFeedbackRequestDTO requestDTO) {
        return ResponseEntity.ok(service.create(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassFeedbackResponseDTO> update(@PathVariable Long id, @RequestBody ClassFeedbackRequestDTO requestDTO) {
        return ResponseEntity.ok(service.update(id, requestDTO));
    }

    @PatchMapping("/{id}/text")
    public ResponseEntity<ClassFeedbackResponseDTO> editText(@PathVariable Long id, @RequestParam String text) {
        return ResponseEntity.ok(service.editText(id, text));
    }

    @GetMapping
    public ResponseEntity<Page<ClassFeedbackResponseDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/council/{councilId}")
    public ResponseEntity<Page<ClassFeedbackResponseDTO>> findByCouncil(@PathVariable Long councilId, Pageable pageable) {
        return ResponseEntity.ok(service.findByCouncil(councilId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassFeedbackResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
