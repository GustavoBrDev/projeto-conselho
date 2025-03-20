package conselho.estudante.com.projetoconselho.SERVICES.ADMINISTRATION;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.SubjectRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.SubjectResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Subject;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.DadosDuplicadosException;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.ADMINISTRATION.SubjectRepository;
import conselho.estudante.com.projetoconselho.REPOSITORIES.USERS.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço para gerenciar operações relacionadas à entidade {@link Subject}.
 *
 * @author Joana Voigt
 * @since 17/03/2025
 *
 * @see Subject
 * @see SubjectRequestDTO
 * @see SubjectResponseDTO
 */
@Service
@AllArgsConstructor
public class SubjectService {

    private SubjectRepository repository;
    private TeacherRepository teacherRepository;

    /**
     * Cria uma nova matéria.
     *
     * @param subjectRequestDTO Dados da matéria a ser criada.
     * @return DTO da resposta contendo a matéria criada.
     * @throws DadosDuplicadosException se a matéria já existir.
     */
    public SubjectResponseDTO create(SubjectRequestDTO subjectRequestDTO) {
        Subject subject = subjectRequestDTO.convert();
        if (repository.existsByName(subject.getName())) {
            throw new DadosDuplicadosException("Matéria já cadastrada");
        } else {
            return repository.save(subject).toDTO();
        }
    }

    /**
     * Atualiza uma matéria existente.
     *
     * @param id ID da matéria a ser atualizada.
     * @param subjectRequestDTO Novos dados da matéria.
     * @return DTO da resposta contendo a matéria atualizada.
     * @throws DadosDuplicadosException se a matéria já existir.
     * @throws NaoEncontradoException se a matéria não for encontrada.
     */
    public SubjectResponseDTO update(Long id, SubjectRequestDTO subjectRequestDTO) {
        Subject subject = subjectRequestDTO.convert();
        if (repository.existsById(id)) {
            subject.setId(id);
            if (repository.existsByName(subject.getName())) {
                throw new DadosDuplicadosException("Matéria já cadastrada");
            } else {
                return repository.save(subject).toDTO();
            }
        }
        throw new NaoEncontradoException("Matéria nao encontrada");
    }

    /**
     * Edita o nome de uma matéria específica.
     *
     * @param id ID da matéria.
     * @param name Novo nome.
     * @return DTO da resposta contendo a matéria atualizada.
     */
    public SubjectResponseDTO editName(Long id, String name) {
        Subject subject = repository.findById(id).get();
        subject.setName(name);
        return repository.save(subject).toDTO();
    }

    /**
     * Edita a carga horária de uma matéria específica.
     *
     * @param id ID da matéria.
     * @param workLoad Nova carga horária.
     * @return DTO da resposta contendo a matéria atualizada.
     */
    public SubjectResponseDTO editWorkLoad(Long id, Integer workLoad) {
        Subject subject = repository.findById(id).get();
        subject.setWorkLoad(workLoad);
        return repository.save(subject).toDTO();
    }

    /**
     * Retorna uma lista paginada de matérias.
     *
     * @param pageable Informações de paginação.
     * @return Página de matérias na forma de DTOs de resposta.
     * @throws NaoEncontradoException se nenhuma matéria for encontrada.
     */
    public Page<SubjectResponseDTO> findSubjects(Pageable pageable) {
        try {
            return repository.findAll(pageable).map(Subject::toDTO);
        } catch (Exception e) {
            throw new NaoEncontradoException("Matéria nao encontrada");
        }
    }

    /**
     * Retorna uma lista paginada de professores associados a uma matéria.
     *
     * @param subjectId ID da matéria.
     * @param pageable Informações de paginação.
     * @return Página de professores associados à matéria.
     * @throws NaoEncontradoException se a matéria não for encontrada.
     */
    public Page<Teacher> findTeachersBySubject(Long subjectId, Pageable pageable) {
        Subject subject = repository.findById(subjectId)
                .orElseThrow(() -> new NaoEncontradoException("Matéria não encontrada"));
        List<Teacher> teachers = subject.getTeachers();
        return new PageImpl<>(teachers, pageable, teachers.size());
    }

    /**
     * Busca uma matéria pelo seu ID.
     *
     * @param id ID da matéria.
     * @return DTO da resposta contendo a matéria encontrada.
     * @throws NaoEncontradoException se a matéria não for encontrada.
     */
    public SubjectResponseDTO findSubjectById(Long id) {
        try {
            return repository.findById(id).get().toDTO();
        } catch (Exception e) {
            throw new NaoEncontradoException("Matéria nao encontrada");
        }
    }

    /**
     * Adiciona um professor a uma matéria.
     *
     * @param subject Matéria à qual o professor será adicionado.
     * @param teacher Professor a ser adicionado.
     * @return DTO da resposta contendo a matéria atualizada.
     * @throws NaoEncontradoException se o professor não for encontrado.
     */
    public SubjectResponseDTO addTeacherToSubject(Subject subject, Teacher teacher) {
        if(subject.addTeacher(teacher)) {
            return repository.save(subject).toDTO();
        } else {
            throw new NaoEncontradoException("Professor nao encontrado");
        }
    }

    /**
     * Remove um professor de uma matéria.
     *
     * @param subject Matéria da qual o professor será removido.
     * @param teacher Professor a ser removido.
     * @return DTO da resposta contendo a matéria atualizada.
     * @throws NaoEncontradoException se o professor não for encontrado.
     */
    public SubjectResponseDTO removeTeacherFromSubject(Subject subject, Teacher teacher) {
        if(subject.removeTeacher(teacher)) {
            return repository.save(subject).toDTO();
        } else {
            throw new NaoEncontradoException("Professor nao encontrado");
        }
    }

    /**
     * Deleta uma matéria pelo seu ID.
     *
     * @param id ID da matéria a ser deletada.
     * @throws NaoEncontradoException se a matéria não for encontrada.
     */
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new NaoEncontradoException("Matéria nao encontrada");
        }
    }

    /**
     * Filtra matérias com base em um termo de busca.
     *
     * @param termo Termo utilizado para busca.
     * @param pageable Informações de paginação.
     * @return Página de matérias que correspondem ao termo de busca na forma de DTOs de resposta.
     */
    public Page<SubjectResponseDTO> subjectFilter(String termo, Pageable pageable) {
        return repository.findAll(SubjectSpecification.subjectFilter(termo), pageable).map(Subject::toDTO);
    }

    /**
     * Busca as matérias associadas a um professor específico.
     *
     * @param teacherId ID do professor.
     * @param pageable Informações de paginação.
     * @return Página de matérias associadas ao professor na forma de DTOs de resposta.
     * @throws NaoEncontradoException se o professor não for encontrado.
     */
    public Page<SubjectResponseDTO> findSubjectsByTeacher(Long teacherId, Pageable pageable) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new NaoEncontradoException("Professor não encontrado"));
        List<Subject> subjects = teacher.getSubjects(); // Recupera as matérias associadas ao professor
        return new PageImpl<>(subjects, pageable, subjects.size()).map(Subject::toDTO);
    }

}
