package conselho.estudante.com.projetoconselho.Controller;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.USERS.RepresentativeRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS.RepresentativeResponseDTO;
import conselho.estudante.com.projetoconselho.SERVICES.USERS.RepresentativeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/representatives")
@RequiredArgsConstructor
public class RepresentativeController {

    private final RepresentativeService service;

    @PostMapping
    public ResponseEntity<RepresentativeResponseDTO> create(
            @RequestBody @Valid RepresentativeRequestDTO dto,
            UriComponentsBuilder uriBuilder) {
        RepresentativeResponseDTO response = service.create(dto);
        URI uri = uriBuilder.path("/api/representatives/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RepresentativeResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid RepresentativeRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping
    public ResponseEntity<Page<RepresentativeResponseDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepresentativeResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PatchMapping("/{id}/students/{studentId}")
    public ResponseEntity<RepresentativeResponseDTO> addStudent(
            @PathVariable Long id,
            @PathVariable Long studentId) {
        return ResponseEntity.ok(service.addStudent(id, studentId));
    }

    @DeleteMapping("/{id}/students/{studentId}")
    public ResponseEntity<RepresentativeResponseDTO> removeStudent(
            @PathVariable Long id,
            @PathVariable Long studentId) {
        return ResponseEntity.ok(service.removeStudent(id, studentId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
