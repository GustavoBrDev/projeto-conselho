package conselho.estudante.com.projetoconselho.SERVICES.EDUCATIONAL;
import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.TeacherPreCouncilRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.StudentResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.TeacherPreCouncilResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Subject;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.TeacherPreCouncil;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Student;
import conselho.estudante.com.projetoconselho.REPOSITORIES.ADMINISTRATION.SubjectRepository;
import conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL.TeacherPreCouncilRepository;
import conselho.estudante.com.projetoconselho.REPOSITORIES.USERS.StudentRepository;
import conselho.estudante.com.projetoconselho.SERVICES.USERS.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * Serviço que gerencia as Pré-Conselhos de Professores (TeacherPreCouncil) da aplicação.
 * Contém métodos para adicionar, buscar, atualizar, excluir e listar pré-conselhos de professores,
 * bem como manipular seus relacionamentos com matérias e alunos.
 *
 * @author Cauã Justimiano Dutra
 * @since 17/03/2025
 */
@AllArgsConstructor
@Service
public class TeacherPreCouncilService {

    TeacherPreCouncilRepository repository;
    SubjectRepository subjectRepository;
    StudentRepository studentRepository;
    StudentService studentService;

    /**
     * Adiciona um novo pré-conselho de professor à aplicação.
     *
     * @param teacherPreCouncilRequestDTO Objeto contendo os dados do pré-conselho a ser adicionado.
     * @return O pré-conselho adicionado, convertido para DTO.
     */
        public TeacherPreCouncilResponseDTO add(TeacherPreCouncilRequestDTO teacherPreCouncilRequestDTO) {
            TeacherPreCouncil teacherPreCouncil = repository.save(toEntity(teacherPreCouncilRequestDTO));
            return teacherPreCouncil.toDTO();
        }

    /**
     * Pesquisa um pré-conselho de professor pelo seu ID.
     *
     * @param id ID do pré-conselho a ser buscado.
     * @return O pré-conselho encontrado, convertido para DTO.
     * @throws NoSuchElementException Caso o pré-conselho não seja encontrado.
     */
        public TeacherPreCouncilResponseDTO search(Long id) {
            return repository.findById(id).orElseThrow(NoSuchElementException::new).toDTO();
        }

    /**
     * Atualiza os dados de um pré-conselho de professor.
     *
     * @param teacherPreCouncilRequestDTO Objeto contendo os novos dados do pré-conselho.
     * @param id ID do pré-conselho a ser atualizado.
     * @return O pré-conselho atualizado, convertido para DTO.
     * @throws NoSuchElementException Caso o pré-conselho não seja encontrado.
     */
        public TeacherPreCouncilResponseDTO update(TeacherPreCouncilRequestDTO teacherPreCouncilRequestDTO, Long id) {
            search(id);

            TeacherPreCouncil teacherPreCouncil = repository.save(toEntity(teacherPreCouncilRequestDTO));
            return teacherPreCouncil.toDTO();
        }

    /**
     * Exclui um pré-conselho de professor da aplicação.
     *
     * @param id ID do pré-conselho a ser excluído.
     * @return O pré-conselho excluído, convertido para DTO.
     * @throws NoSuchElementException Caso o pré-conselho não seja encontrado.
     */
        public TeacherPreCouncilResponseDTO delete(Long id) {
            TeacherPreCouncilResponseDTO teacherPreCouncilResponseDTO = search(id);

            repository.deleteById(id);
            return teacherPreCouncilResponseDTO;
        }

    /**
     * Atualiza a data de início de um pré-conselho de professor.
     *
     * @param id ID do pré-conselho a ser atualizado.
     * @param startDate Nova data de início.
     * @return O pré-conselho atualizado, convertido para DTO.
     * @throws NoSuchElementException Caso o pré-conselho não seja encontrado.
     */
    public TeacherPreCouncilResponseDTO updateStartDate(Long id, Date startDate) {
        TeacherPreCouncil teacherPreCouncil = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("TeacherPreCouncil not found"));

        teacherPreCouncil.setStartDate(startDate);
        teacherPreCouncil = repository.save(teacherPreCouncil);
        return teacherPreCouncil.toDTO();
    }

    /**
     * Atualiza a data de término de um pré-conselho de professor.
     *
     * @param id ID do pré-conselho a ser atualizado.
     * @param endDate Nova data de término.
     * @return O pré-conselho atualizado, convertido para DTO.
     * @throws NoSuchElementException Caso o pré-conselho não seja encontrado.
     */
    public TeacherPreCouncilResponseDTO updateEndDate(Long id, Date endDate) {
        TeacherPreCouncil teacherPreCouncil = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("TeacherPreCouncil not found"));

        teacherPreCouncil.setEndDate(endDate);
        teacherPreCouncil = repository.save(teacherPreCouncil);
        return teacherPreCouncil.toDTO();
    }

    /**
     * Atualiza o estado de preenchimento de um pré-conselho de professor.
     *
     * @param id ID do pré-conselho a ser atualizado.
     * @param isFilled Novo estado de preenchimento (true/false).
     * @return O pré-conselho atualizado, convertido para DTO.
     * @throws NoSuchElementException Caso o pré-conselho não seja encontrado.
     */
    public TeacherPreCouncilResponseDTO updateIsFilled(Long id, Boolean isFilled) {
        TeacherPreCouncil teacherPreCouncil = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("TeacherPreCouncil not found"));

        teacherPreCouncil.setIsFilled(isFilled);
        teacherPreCouncil = repository.save(teacherPreCouncil);
        return teacherPreCouncil.toDTO();
    }

    /**
     * Atualiza a matéria associada a um pré-conselho de professor.
     *
     * @param id ID do pré-conselho a ser atualizado.
     * @param subjectId ID da matéria a ser associada.
     * @return O pré-conselho atualizado, convertido para DTO.
     * @throws NoSuchElementException Caso o pré-conselho ou a matéria não sejam encontrados.
     */
    public TeacherPreCouncilResponseDTO updateSubject(Long id, Long subjectId) {
        TeacherPreCouncil teacherPreCouncil = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("TeacherPreCouncil not found"));

        // Você pode usar um serviço para buscar a matéria (Subject) se necessário
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new NoSuchElementException("Subject not found"));

        teacherPreCouncil.setSubject(subject);
        teacherPreCouncil = repository.save(teacherPreCouncil);
        return teacherPreCouncil.toDTO();
    }

    /**
     * Lista todos os pré-conselhos de professores, com paginação.
     *
     * @param pageable Objeto que contém informações de paginação (tamanho e número da página).
     * @return Página contendo os pré-conselhos de professores, convertidos para DTO.
     */
    public Page<TeacherPreCouncilResponseDTO> listAllTeacherPreCouncils(Pageable pageable) {
        return repository.findAll(pageable)
                .map(TeacherPreCouncil::toDTO);
    }

    /**
     * Lista todos os alunos da aplicação, com paginação.
     *
     * @param pageable Objeto que contém informações de paginação (tamanho e número da página).
     * @return Página contendo os alunos, convertidos para DTO.
     */
    public Page<StudentResponseDTO> listAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable)
                .map(Student::convert);
    }


    /**
     * Método que realiza a pesquisa de pré-conselhos de professores com filtros aplicados.
     * Os filtros são opcionais, então pode ser fornecido apenas um ou vários.
     *
     * @param texto Texto para pesquisa inteligente (nome do professor, matéria, curso).
     * @param professor Nome do professor (opcional).
     * @param materia Nome da matéria (opcional).
     * @param curso Nome do curso (opcional).
     * @return Lista de pré-conselhos de professores com os filtros aplicados.
     */
    public List<TeacherPreCouncil> searchTeacherPreCouncil(String texto, String professor, String materia, String curso) {
        Specification<TeacherPreCouncil> specification = Specification
                .where(TeacherPreCouncilSpecification.filtroContendoTexto(texto))  // Pesquisa inteligente
                .and(TeacherPreCouncilSpecification.filtroPorProfessor(professor))  // Filtro por professor
                .and(TeacherPreCouncilSpecification.filtroPorMateria(materia))  // Filtro por matéria
                .and(TeacherPreCouncilSpecification.filtroPorCurso(curso));  // Filtro por curso

        // Retorna a lista de resultados com os filtros aplicados
        return repository.findAll(specification);
    }

    /**
     * Converte um DTO de solicitação de pré-conselho de professor para a entidade correspondente.
     *
     * @param requestDTO DTO com os dados de solicitação de pré-conselho.
     * @return Entidade TeacherPreCouncil correspondente.
     */
    public TeacherPreCouncil toEntity(TeacherPreCouncilRequestDTO requestDTO) {
        return TeacherPreCouncil.builder()
                .teacher(TeacherService.buscarProfessorEntidade(requestDTO.teacher_id())) // TROCAR QUANDO A TEACHER SERVICE ESTIVER FEITA
                .createdAt(new Date()) // Define a data de criação
                .startDate(null) // Será definido depois
                .endDate(null) // Será definido depois
                .council(null) // Será definido quando vincular a um conselho
                .classe(null) // Será definido depois
                .isFilled(false) // Começa como não preenchido
                .subject(null) // Será definido depois
                .feedbacks(new ArrayList<>()) // Começa com uma lista vazia
                .build();
    }
}



