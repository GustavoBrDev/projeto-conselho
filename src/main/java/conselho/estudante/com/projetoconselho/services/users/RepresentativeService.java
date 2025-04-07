package conselho.estudante.com.projetoconselho.services.users;


import conselho.estudante.com.projetoconselho.models.dto.request.USERS.RepresentativeRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.USERS.RepresentativeResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.administration.Classe;
import conselho.estudante.com.projetoconselho.models.entity.users.Representative;
import conselho.estudante.com.projetoconselho.models.entity.users.Student;
import conselho.estudante.com.projetoconselho.models.exceptions.DadosDuplicadosException;
import conselho.estudante.com.projetoconselho.models.exceptions.LimiteNotificacoesException;
import conselho.estudante.com.projetoconselho.models.exceptions.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.repositories.administration.ClasseRepository;
import conselho.estudante.com.projetoconselho.repositories.users.RepresentativeRepository;
import conselho.estudante.com.projetoconselho.repositories.users.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Service
@RequiredArgsConstructor
public class RepresentativeService {


    private final RepresentativeRepository representativeRepository;
    private final ClasseRepository classeRepository;
    private final StudentRepository studentRepository;
    private static final int MAX_REPRESENTATIVES = 2;


    @Transactional
    public RepresentativeResponseDTO create(RepresentativeRequestDTO dto) {
        if (representativeRepository.existsByRepresentativeOfId(dto.classeId())) {
            throw new DadosDuplicadosException("Já existe representação para esta classe");
        }


        Classe classe = classeRepository.findById(dto.classeId())
                .orElseThrow(() -> new NaoEncontradoException("Classe não encontrada"));


        validateRepresentativesLimit(dto.studentsIds().size());


        List<Student> students = getValidStudents(dto.studentsIds());


        Representative representative = new Representative();
        representative.setRepresentativeOf(classe);
        representative.setStudents(students);


        return toDTO(representativeRepository.save(representative));
    }


    @Transactional
    public RepresentativeResponseDTO update(Long id, RepresentativeRequestDTO dto) {
        Representative representative = representativeRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Representação não encontrada"));


        Classe classe = classeRepository.findById(dto.classeId())
                .orElseThrow(() -> new NaoEncontradoException("Classe não encontrada"));


        validateRepresentativesLimit(dto.studentsIds().size());


        List<Student> students = getValidStudents(dto.studentsIds());


        representative.setRepresentativeOf(classe);
        representative.setStudents(students);


        return toDTO(representativeRepository.save(representative));
    }


    public Page<RepresentativeResponseDTO> findAll(Pageable pageable) {
        return representativeRepository.findAll(pageable)
                .map(this::toDTO);
    }


    public RepresentativeResponseDTO findById(Long id) {
        return representativeRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new NaoEncontradoException("Representação não encontrada"));
    }


    @Transactional
    public RepresentativeResponseDTO addStudent(Long id, Long studentId) {
        Representative representative = representativeRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Representação não encontrada"));


        if (representative.getStudents().size() >= MAX_REPRESENTATIVES) {
            throw new LimiteNotificacoesException(
                    "Limite máximo de " + MAX_REPRESENTATIVES + " representantes atingido");
        }


        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NaoEncontradoException("Estudante não encontrado"));


        if (representative.getStudents().contains(student)) {
            throw new DadosDuplicadosException("Estudante já é representante");
        }


        representative.getStudents().add(student);
        return toDTO(representativeRepository.save(representative));
    }


    @Transactional
    public RepresentativeResponseDTO removeStudent(Long id, Long studentId) {
        Representative representative = representativeRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Representação não encontrada"));


        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NaoEncontradoException("Estudante não encontrado"));


        if (!representative.getStudents().contains(student)) {
            throw new NaoEncontradoException("Estudante não é representante");
        }


        representative.getStudents().remove(student);
        return toDTO(representativeRepository.save(representative));
    }


    @Transactional
    public void delete(Long id) {
        if (!representativeRepository.existsById(id)) {
            throw new NaoEncontradoException("Representação não encontrada");
        }
        representativeRepository.deleteById(id);
    }


    private List<Student> getValidStudents(List<Long> studentsIds) {
        return studentsIds.stream()
                .map(id -> studentRepository.findById(id)
                        .orElseThrow(() -> new NaoEncontradoException("Estudante não encontrado")))
                .toList();
    }


    private void validateRepresentativesLimit(int count) {
        if (count > MAX_REPRESENTATIVES) {
            throw new LimiteNotificacoesException(
                    "Número máximo de representantes é " + MAX_REPRESENTATIVES);
        }
    }


    private RepresentativeResponseDTO toDTO(Representative representative) {
        RepresentativeResponseDTO dto = new RepresentativeResponseDTO();
        dto.setId(representative.getId());
        dto.setStudents(representative.getStudents());
        dto.setRepresentativeOf(representative.getRepresentativeOf());
        return dto;
    }
}
