package conselho.estudante.com.projetoconselho.SERVICES.USERS;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.USERS.RepresentativeRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS.RepresentativeResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.DadosDuplicadosException;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.LimiteExcedidoException;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Representative;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Student;
import conselho.estudante.com.projetoconselho.REPOSITORIES.ADMINISTRATION.ClasseRepository;
import conselho.estudante.com.projetoconselho.REPOSITORIES.USERS.RepresentativeRepository;
import conselho.estudante.com.projetoconselho.REPOSITORIES.USERS.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Serviço para gestão de representantes de classe
 */
@Service
@AllArgsConstructor
public class RepresentativeService {

    private final RepresentativeRepository representativeRepository;
    private final StudentRepository studentRepository;
    private final ClasseRepository classeRepository;
    private static final int MAX_REPRESENTATIVES_PER_CLASSE = 2;

    /**
     * Cria uma nova representação de classe
     * @param requestDTO Dados para criação da representação
     * @return DTO com os dados da representação criada
     * @throws DadosDuplicadosException Se a classe já tiver representantes ou se o estudante já for representante
     * @throws LimiteExcedidoException Se exceder o limite de representantes por classe
     * @throws NaoEncontradoException Se classe ou estudante não forem encontrados
     */
    public RepresentativeResponseDTO create(RepresentativeRequestDTO requestDTO) {
        if (representativeRepository.existsByClasseId(requestDTO.classeId())) {
            throw new DadosDuplicadosException("Esta classe já possui representantes");
        }

        Classe classe = classeRepository.findById(requestDTO.classeId())
                .orElseThrow(() -> new NaoEncontradoException("Classe não encontrada"));

        List<Student> students = new ArrayList<>();
        for (Long studentId : requestDTO.studentIds()) {
            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new NaoEncontradoException("Estudante não encontrado"));

            if (representativeRepository.existsByStudentId(student.getId())) {
                throw new DadosDuplicadosException("Estudante já é representante em outra classe");
            }

            students.add(student);
        }

        if (students.size() > MAX_REPRESENTATIVES_PER_CLASSE) {
            throw new LimiteExcedidoException("Número máximo de representantes por classe é " + MAX_REPRESENTATIVES_PER_CLASSE);
        }

        Representative representative = new Representative();
        representative.setStudents(students);
        representative.setRepresentativeOf(classe);

        return convertToDTO(representativeRepository.save(representative));
    }

    /**
     * Atualiza uma representação existente
     * @param id ID da representação a ser atualizada
     * @param requestDTO Dados para atualização
     * @return DTO com os dados atualizados
     * @throws DadosDuplicadosException Se a nova classe já tiver representantes ou se o estudante já for representante
     * @throws LimiteExcedidoException Se exceder o limite de representantes
     * @throws NaoEncontradoException Se representação, classe ou estudante não forem encontrados
     */
    public RepresentativeResponseDTO update(Long id, RepresentativeRequestDTO requestDTO) {
        Representative representative = getRepresentativeById(id);

        if (!representative.getRepresentativeOf().getId().equals(requestDTO.classeId())) {
            if (representativeRepository.existsByClasseId(requestDTO.classeId())) {
                throw new DadosDuplicadosException("A nova classe já possui representantes");
            }

            Classe newClasse = classeRepository.findById(requestDTO.classeId())
                    .orElseThrow(() -> new NaoEncontradoException("Classe não encontrada"));
            representative.setRepresentativeOf(newClasse);
        }

        List<Student> newStudents = new ArrayList<>();
        for (Long studentId : requestDTO.studentIds()) {
            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new NaoEncontradoException("Estudante não encontrado"));

            if (representativeRepository.existsByStudentId(student.getId()) &&
                    !representative.getStudents().contains(student)) {
                throw new DadosDuplicadosException("Estudante já é representante em outra classe");
            }

            newStudents.add(student);
        }

        if (newStudents.size() > MAX_REPRESENTATIVES_PER_CLASSE) {
            throw new LimiteExcedidoException("Número máximo de representantes por classe é " + MAX_REPRESENTATIVES_PER_CLASSE);
        }

        representative.setStudents(newStudents);
        return convertToDTO(representativeRepository.save(representative));
    }

    /**
     * Adiciona um representante à representação existente
     * @param representativeId ID da representação
     * @param studentId ID do estudante a ser adicionado como representante
     * @return DTO com os dados atualizados
     * @throws LimiteExcedidoException Se exceder o limite de representantes
     * @throws DadosDuplicadosException Se o estudante já for representante em outra classe
     * @throws NaoEncontradoException Se representação ou estudante não forem encontrados
     */
    public RepresentativeResponseDTO addRepresentative(Long representativeId, Long studentId) {
        Representative representative = getRepresentativeById(representativeId);

        if (representative.getStudents().size() >= MAX_REPRESENTATIVES_PER_CLASSE) {
            throw new LimiteExcedidoException("Número máximo de representantes por classe é " + MAX_REPRESENTATIVES_PER_CLASSE);
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NaoEncontradoException("Estudante não encontrado"));

        if (representativeRepository.existsByStudentId(student.getId())) {
            throw new DadosDuplicadosException("Este estudante já é representante em outra classe");
        }

        if (!representative.getStudents().contains(student)) {
            representative.getStudents().add(student);
        }

        return convertToDTO(representativeRepository.save(representative));
    }

    /**
     * Remove um representante da representação
     * @param representativeId ID da representação
     * @param studentId ID do estudante a ser removido
     * @return DTO com os dados atualizados ou null se a representação ficar vazia
     * @throws NaoEncontradoException Se representação, estudante ou vínculo não forem encontrados
     */
    public RepresentativeResponseDTO removeRepresentative(Long representativeId, Long studentId) {
        Representative representative = getRepresentativeById(representativeId);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NaoEncontradoException("Estudante não encontrado"));

        if (!representative.getStudents().remove(student)) {
            throw new NaoEncontradoException("Estudante não encontrado nesta representação");
        }

        if (representative.getStudents().isEmpty()) {
            representativeRepository.delete(representative);
            return null;
        }

        return convertToDTO(representativeRepository.save(representative));
    }

    /**
     * Lista todas as representações com paginação
     * @param pageable Configuração de paginação
     * @return Página com representações
     * @throws NaoEncontradoException Se nenhuma representação for encontrada
     */
    public Page<RepresentativeResponseDTO> findAll(Pageable pageable) {
        Page<Representative> representatives = representativeRepository.findAll(pageable);
        if (representatives.isEmpty()) {
            throw new NaoEncontradoException("Nenhuma representação encontrada");
        }
        return representatives.map(this::convertToDTO);
    }

    /**
     * Busca uma representação por ID
     * @param id ID da representação
     * @return DTO com os dados da representação
     * @throws NaoEncontradoException Se a representação não for encontrada
     */
    public RepresentativeResponseDTO findById(Long id) {
        return convertToDTO(getRepresentativeById(id));
    }

    /**
     * Remove uma representação
     * @param id ID da representação a ser removida
     * @throws NaoEncontradoException Se a representação não for encontrada
     */
    public void delete(Long id) {
        if (!representativeRepository.existsById(id)) {
            throw new NaoEncontradoException("Representação não encontrada");
        }
        representativeRepository.deleteById(id);
    }

    private Representative getRepresentativeById(Long id) {
        return representativeRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Representação não encontrada"));
    }

    private RepresentativeResponseDTO convertToDTO(Representative representative) {
        return new RepresentativeResponseDTO(
                representative.getId(),
                representative.getStudents(),
                representative.getRepresentativeOf()
        );
    }
}