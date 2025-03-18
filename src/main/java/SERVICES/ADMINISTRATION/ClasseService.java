package SERVICES.ADMINISTRATION;

import MODELS.DTO.REQUEST.ClasseRequestDTO;
import MODELS.DTO.RESPONSE.ClasseResponseDTO;
import MODELS.ENTITY.ADMINISTRATION.Classe;
import MODELS.ENTITY.ADMINISTRATION.Course;
import MODELS.EXCEPTIONS.DadosDuplicadosException;
import MODELS.EXCEPTIONS.NaoEncontradoException;
import REPOSITORIES.ADMINISTRATION.ClasseRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClasseService {

    private ClasseRepository repository;

    public ClasseResponseDTO create(ClasseRequestDTO classeRequestDTO) {
        Classe classe = classeRequestDTO.convert();
        if(repository.existsByName(classe.getName())) {
            throw new DadosDuplicadosException("Turma ja cadastrada");
        } else if (repository.existsByAcronym(classe.getAcronym())) {
            throw new DadosDuplicadosException("Sigla ja cadastrada");
        } else {
            return repository.save(classe).toDTO();
        }
    }

    public ClasseResponseDTO update(Long id, ClasseRequestDTO classeRequestDTO) {
        Classe classe = classeRequestDTO.convert();
        if (repository.existsById(id)) {
            classe.setId(id);
            if (repository.existsByName(classe.getName())) {
                throw new DadosDuplicadosException("Turma ja cadastrada");
            } else if (repository.existsByAcronym(classe.getAcronym())) {
                throw new DadosDuplicadosException("Sigla ja cadastrada");
            }
            return repository.save(classe).toDTO();
        }
        throw new DadosDuplicadosException("Turma nao encontrada");
    }

    public ClasseResponseDTO editName(Long id, String name) {
        Classe classe = repository.findById(id).get();
        classe.setName(name);
        return repository.save(classe).toDTO();
    }

    public ClasseResponseDTO editAcronym(Long id, String acronym) {
        Classe classe = repository.findById(id).get();
        classe.setAcronym(acronym);
        return repository.save(classe).toDTO();
    }

    public ClasseResponseDTO editCourse(Long id, Course course) {
        Classe classe = repository.findById(id).get();
        classe.setCourse(course);
        return repository.save(classe).toDTO();
    }

    public ClasseResponseDTO editActive(Long id, boolean active) {
        Classe classe = repository.findById(id).get();
        classe.setActive(active);
        return repository.save(classe).toDTO();
    }

    public Page<ClasseResponseDTO> findClasses(Pageable pageable) {
        try {
            return repository.findAll(pageable).map(Classe::toDTO);
        } catch (Exception e) {
            throw new NaoEncontradoException("Turma nao encontrada");
        }
    }

    
}
