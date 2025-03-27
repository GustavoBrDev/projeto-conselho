package conselho.estudante.com.projetoconselho.SERVICES.EDUCATIONAL.TEACHER_PRE_COUNCIL;
import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.EDUCATIONAL.PersonalFeedbackRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.EDUCATIONAL.TeacherPreCouncilRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL.TeacherPreCouncilResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS.StudentResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Subject;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.PersonalFeedback;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.TeacherPreCouncil;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.AddItem;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.ChangeItem;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.EditableItem;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL.TeacherPreCouncilRepository;
import conselho.estudante.com.projetoconselho.SERVICES.ADMINISTRATION.SUBJECT.SubjectService;
import conselho.estudante.com.projetoconselho.SERVICES.LOGS.PreCouncilLogsService;
import conselho.estudante.com.projetoconselho.SERVICES.USERS.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;


/**
 * Serviço que gerencia as Pré-Conselhos de Professores (TeacherPreCouncil) da aplicação.
 * Contém métodos para adicionar, buscar, atualizar, excluir e listar pré-conselhos de professores,
 * bem como manipular seus relacionamentos com matérias e alunos.
 *
 * @author Cauã Justimiano Dutra
 * @since 17/03/2025
 *
 * Atualizado em 27/03/2025
 * Conexão com o PreCouncilLogsService para gerar logs
 * @author Gustavo Stinghen
 * @see PreCouncilLogsService
 */
@AllArgsConstructor
@Service
public class TeacherPreCouncilService {

    private TeacherPreCouncilRepository repository;
    private StudentService studentService;
    private SubjectService subjectService;
    private PreCouncilLogsService logsService;

    /**
     * Adiciona um novo pré-conselho de professor à aplicação.
     *
     * @param teacherPreCouncilRequestDTO Objeto contendo os dados do pré-conselho a ser adicionado.
     * @return O pré-conselho adicionado, convertido para DTO.
     */
        public TeacherPreCouncilResponseDTO create(TeacherPreCouncilRequestDTO teacherPreCouncilRequestDTO) {

           try {

                TeacherPreCouncil teacherPreCouncil = teacherPreCouncilRequestDTO.toEntity();
                teacherPreCouncil.setIsFilled(false);
                teacherPreCouncil.setFeedbacks(new ArrayList<>());
                teacherPreCouncil.setStartDate(new Date());
                teacherPreCouncil.setCreatedAt(new Date());
                teacherPreCouncil = repository.save(teacherPreCouncil);
                logsService.create(teacherPreCouncil, "create");
                return teacherPreCouncil.toDTO();

            } catch (Exception e) {
                throw new RuntimeException("Erro ao adicionar pré-conselho de professor", e);
            }
        }

    /**
     * Pesquisa um pré-conselho de professor pelo seu ID.
     *
     * @param id ID do pré-conselho a ser buscado.
     * @return O pré-conselho encontrado, convertido para DTO.
     * @throws NoSuchElementException Caso o pré-conselho não seja encontrado.
     */
    public TeacherPreCouncilResponseDTO search(Long id) {
        return repository.findById(id).orElseThrow(NaoEncontradoException::new).toDTO();
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

        try {

            if ( search(id) == null ) {
                throw new NaoEncontradoException("Pré conselho de professor nao encontrado");
            }

            TeacherPreCouncil teacherPreCouncil = teacherPreCouncilRequestDTO.toEntity();
            teacherPreCouncil.setId(id);
            logsService.create(teacherPreCouncil, getEditableItems(repository.findById(id).get(), teacherPreCouncil), "update");
            return repository.save(teacherPreCouncil).toDTO();

        } catch (Exception e) {
            throw new NaoEncontradoException("Erro ao atualizar pré-conselho de professor", e);
        }
    }

    /**
     * Obtem os itens editáveis de um pré-conselho de professor.
     *
     * @param oldTeacherPreCouncil Pré-conselho de professor antigo.
     * @param newTeacherPreCouncil Pré-conselho de professor novo.
     * @return Lista de itens editáveis.
     */
    private List<EditableItem> getEditableItems(TeacherPreCouncil oldTeacherPreCouncil, TeacherPreCouncil newTeacherPreCouncil) {
        List<EditableItem> changes = new ArrayList<>();

        if ( !oldTeacherPreCouncil.getEndDate().equals(newTeacherPreCouncil.getEndDate()) ) {
            changes.add(new ChangeItem("endDate", oldTeacherPreCouncil.getEndDate(), newTeacherPreCouncil.getEndDate()));
        }

        if ( !oldTeacherPreCouncil.getClasse().equals(newTeacherPreCouncil.getClasse()) ) {
            changes.add(new ChangeItem("classe", oldTeacherPreCouncil.getClasse(), newTeacherPreCouncil.getClasse()));
        }

        if ( !oldTeacherPreCouncil.getStartDate().equals(newTeacherPreCouncil.getStartDate()) ) {
            changes.add(new ChangeItem("startDate", oldTeacherPreCouncil.getStartDate(), newTeacherPreCouncil.getStartDate()));
        }

        return changes;
    }

    /**
     * Exclui um pré-conselho de professor da aplicação.
     *
     * @param id ID do pré-conselho a ser excluído.
     * @return O pré-conselho excluído, convertido para DTO.
     * @throws NoSuchElementException Caso o pré-conselho não seja encontrado.
     */
    public TeacherPreCouncilResponseDTO delete(Long id) {

        try {

            TeacherPreCouncil teacherPreCouncil = repository.findById(id).orElseThrow(NaoEncontradoException::new);
            logsService.create( teacherPreCouncil, "delete");
            repository.delete(teacherPreCouncil);
            return teacherPreCouncil.toDTO();

        } catch (Exception e) {
            throw new NaoEncontradoException("Erro ao excluir pré-conselho de professor", e);
        }
    }

    /**
     * Atualiza a data de início de um pré-conselho de professor.
     *
     * @param id ID do pré-conselho a ser atualizado.
     * @param startDate Nova data de início.
     * @return O pré-conselho atualizado, convertido para DTO.
     * @throws NoSuchElementException Caso o pré-conselho não seja encontrado.
     */
    public TeacherPreCouncilResponseDTO editStartDate(Long id, Date startDate) {
        TeacherPreCouncil teacherPreCouncil = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pré conselho de professor nao encontrado"));
        Date oldStartDate = teacherPreCouncil.getStartDate();
        teacherPreCouncil.setStartDate(startDate);
        logsService.create( teacherPreCouncil, Collections.singletonList(new ChangeItem("startDate", (Object) oldStartDate, (Object) startDate)), "update");
        return repository.save(teacherPreCouncil).toDTO();
    }

    /**
     * Atualiza a data de término de um pré-conselho de professor.
     *
     * @param id ID do pré-conselho a ser atualizado.
     * @param endDate Nova data de término.
     * @return O pré-conselho atualizado, convertido para DTO.
     * @throws NoSuchElementException Caso o pré-conselho não seja encontrado.
     */
    public TeacherPreCouncilResponseDTO editEndDate(Long id, Date endDate) {
        TeacherPreCouncil teacherPreCouncil = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("TeacherPreCouncil not found"));
        Date oldEndDate = teacherPreCouncil.getEndDate();
        teacherPreCouncil.setEndDate(endDate);
        logsService.create( teacherPreCouncil, Collections.singletonList(new ChangeItem("endDate", (Object) oldEndDate, (Object) endDate)), "update");
        return repository.save(teacherPreCouncil).toDTO();
    }

    /**
     * Atualiza o estado de preenchimento de um pré-conselho de professor.
     *
     * @param id ID do pré-conselho a ser atualizado.
     * @param isFilled Novo estado de preenchimento (true/false).
     * @return O pré-conselho atualizado, convertido para DTO.
     * @throws NoSuchElementException Caso o pré-conselho não seja encontrado.
     */
    public TeacherPreCouncilResponseDTO editIsFilled(Long id, Boolean isFilled) {
        TeacherPreCouncil teacherPreCouncil = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("TeacherPreCouncil not found"));
        boolean oldIsFilled = teacherPreCouncil.getIsFilled();
        teacherPreCouncil.setIsFilled(isFilled);
        logsService.create( teacherPreCouncil, Collections.singletonList(new ChangeItem("isFilled", (Object) oldIsFilled, (Object) isFilled)), "update");
        return repository.save(teacherPreCouncil).toDTO();
    }

    /**
     * Atualiza a matéria associada a um pré-conselho de professor.
     *
     * @param id ID do pré-conselho a ser atualizado.
     * @param subjectId ID da matéria a ser associada.
     * @return O pré-conselho atualizado, convertido para DTO.
     * @throws NoSuchElementException Caso o pré-conselho ou a matéria não sejam encontrados.
     */
    public TeacherPreCouncilResponseDTO editSubject(Long id, Long subjectId) {
        TeacherPreCouncil teacherPreCouncil = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("TeacherPreCouncil not found"));

        Subject subject = subjectService.getObjectSubject(subjectId);
        Subject oldSubject = teacherPreCouncil.getSubject();
        teacherPreCouncil.setSubject(subject);
        logsService.create( teacherPreCouncil, Collections.singletonList(new ChangeItem("subject", (Object) oldSubject, (Object) subject)), "update");
        return repository.save(teacherPreCouncil).toDTO();
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
        return studentService.findStudents(pageable);
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
     * Adiciona um feedback ao pré-conselho de professor.
     *
     * @param id ID do pré-conselho de professor.
     * @param feedback Feedback a ser adicionado.
     * @return O pré-conselho de professor com o feedback adicionado, convertido para DTO.
     * @throws NaoEncontradoException Caso o pré-conselho de professor nao seja encontrado.
     * @author Gustavo Stinghen
     * @since 27/03/2025
     */
    public TeacherPreCouncilResponseDTO addFeedback ( Long id, PersonalFeedbackRequestDTO feedback ) {

        try {
            TeacherPreCouncil teacherPreCouncil = repository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Pré conselho de professor nao encontrado"));

            if ( ! teacherPreCouncil.addFeedback(feedback.convert())){
                throw new NaoEncontradoException("Pré conselho de professor nao encontrado");
            }

            logsService.create( teacherPreCouncil, Collections.singletonList(new AddItem("feedbacks", (Object) feedback)), "add");
            return repository.save(teacherPreCouncil).toDTO();
        } catch (Exception e) {
            throw new NaoEncontradoException("Pré conselho de professor nao encontrado", e);
        }
    }

    /**
     * Remove um feedback do pré-conselho de professor.
     *
     * @param id ID do pré-conselho de professor.
     * @param feedback Feedback a ser removido.
     * @return O pré-conselho de professor com o feedback removido, convertido para DTO.
     * @throws NaoEncontradoException Caso o pré-conselho de professor nao seja encontrado.
     * @author Gustavo Stinghen
     * @since 27/03/2025
     */
    public TeacherPreCouncilResponseDTO removeFeedback ( Long id, PersonalFeedbackRequestDTO feedback ) {

        try {
            TeacherPreCouncil teacherPreCouncil = repository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Pré conselho de professor nao encontrado"));

            if ( ! teacherPreCouncil.removeFeedback(feedback.convert())){
                throw new NaoEncontradoException("Pré conselho de professor nao encontrado");
            }

            logsService.create( teacherPreCouncil, Collections.singletonList(new AddItem("feedbacks", (Object) feedback)), "remove");
            return repository.save(teacherPreCouncil).toDTO();
        } catch (Exception e) {
            throw new NaoEncontradoException("Pré conselho de professor nao encontrado", e);
        }
    }
    
}



