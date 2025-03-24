package conselho.estudante.com.projetoconselho.SERVICES.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.EDUCATIONAL.RepresentativePreCouncilRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL.RepresentativePreCouncilResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.*;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.*;
import conselho.estudante.com.projetoconselho.REPOSITORIES.ADMINISTRATION.ClasseRepository;
import conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL.*;
import conselho.estudante.com.projetoconselho.REPOSITORIES.USERS.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RepresentativePreCouncilService {

    private final RepresentativePreCouncilRepository preCouncilRepository;
    private final CouncilRepository councilRepository;
    private final ClasseRepository classeRepository;
    private final TeacherRepository teacherRepository;
    private final AdvisorFeedbackRepository advisorFeedbackRepository;
    private final SupervisorFeedbackRepository supervisorFeedbackRepository;
    private final TeacherFeedbackRepository teacherFeedbackRepository;
    private final ItemFeedbackRepository itemFeedbackRepository;

    @Transactional
    public RepresentativePreCouncilResponseDTO create(RepresentativePreCouncilRequestDTO requestDTO) {
        validateRequiredFields(requestDTO);
        validateDates(requestDTO.startDate(), requestDTO.endDate());

        Council council = councilRepository.findById(requestDTO.councilId())
                .orElseThrow(() -> new NaoEncontradoException("Conselho não encontrado"));

        Classe classe = classeRepository.findById(requestDTO.classeId())
                .orElseThrow(() -> new NaoEncontradoException("Classe não encontrada"));

        List<Teacher> teachers = getTeachersByIds(requestDTO.teacherIds());
        AdvisorFeeback advisorFeedback = getAdvisorFeedbackById(requestDTO.advisorFeedbackId());
        SupervisorFeedback supervisorFeedback = getSupervisorFeedbackById(requestDTO.supervisorFeedbackId());
        List<ItemFeedback> itemFeedbacks = getItemFeedbacksByIds(requestDTO.itemFeedbackIds());

        RepresentativePreCouncil preCouncil = new RepresentativePreCouncil();
        preCouncil.setCouncil(council);
        preCouncil.setCreatedAt(new Date());
        preCouncil.setStartDate(requestDTO.startDate());
        preCouncil.setEndDate(requestDTO.endDate());
        preCouncil.setClasse(classe);
        preCouncil.setIsFilled(false); // Inicia como não preenchido
        preCouncil.setTeachers(teachers);
        preCouncil.setAdvisorFeeback(advisorFeedback);
        preCouncil.setSupervisorFeeback(supervisorFeedback);
        preCouncil.setTeacherFeebacks(new ArrayList<>());
        preCouncil.setItemFeedbacks(itemFeedbacks);

        return convertToDTO(preCouncilRepository.save(preCouncil));
    }

    @Transactional
    public RepresentativePreCouncilResponseDTO update(Long id, RepresentativePreCouncilRequestDTO requestDTO) {
        validateRequiredFields(requestDTO);
        validateDates(requestDTO.startDate(), requestDTO.endDate());

        RepresentativePreCouncil preCouncil = getPreCouncilById(id);

        Council council = councilRepository.findById(requestDTO.councilId())
                .orElseThrow(() -> new NaoEncontradoException("Conselho não encontrado"));

        Classe classe = classeRepository.findById(requestDTO.classeId())
                .orElseThrow(() -> new NaoEncontradoException("Classe não encontrada"));

        List<Teacher> teachers = getTeachersByIds(requestDTO.teacherIds());
        AdvisorFeeback advisorFeedback = getAdvisorFeedbackById(requestDTO.advisorFeedbackId());
        SupervisorFeedback supervisorFeedback = getSupervisorFeedbackById(requestDTO.supervisorFeedbackId());
        List<ItemFeedback> itemFeedbacks = getItemFeedbacksByIds(requestDTO.itemFeedbackIds());

        preCouncil.setCouncil(council);
        preCouncil.setStartDate(requestDTO.startDate());
        preCouncil.setEndDate(requestDTO.endDate());
        preCouncil.setClasse(classe);
        preCouncil.setTeachers(teachers);
        preCouncil.setAdvisorFeeback(advisorFeedback);
        preCouncil.setSupervisorFeeback(supervisorFeedback);
        preCouncil.setItemFeedbacks(itemFeedbacks);

        return convertToDTO(preCouncilRepository.save(preCouncil));
    }

    @Transactional
    public RepresentativePreCouncilResponseDTO editStartDate(Long id, Date startDate) {
        if (startDate == null) {
            throw new CamposObrigatoriosException("Data de início é obrigatória");
        }

        RepresentativePreCouncil preCouncil = getPreCouncilById(id);
        if (preCouncil.getEndDate() != null && startDate.after(preCouncil.getEndDate())) {
            throw new DataInvalidaException("Data de início não pode ser após a data de fim");
        }

        preCouncil.setStartDate(startDate);
        return convertToDTO(preCouncilRepository.save(preCouncil));
    }

    @Transactional
    public RepresentativePreCouncilResponseDTO editEndDate(Long id, Date endDate) {
        if (endDate == null) {
            throw new CamposObrigatoriosException("Data de fim é obrigatória");
        }

        RepresentativePreCouncil preCouncil = getPreCouncilById(id);
        if (preCouncil.getStartDate() != null && endDate.before(preCouncil.getStartDate())) {
            throw new DataInvalidaException("Data de fim não pode ser antes da data de início");
        }

        preCouncil.setEndDate(endDate);
        return convertToDTO(preCouncilRepository.save(preCouncil));
    }

    @Transactional
    public RepresentativePreCouncilResponseDTO editIsFilled(Long id, Boolean isFilled) {
        if (isFilled == null) {
            throw new CamposObrigatoriosException("Status de preenchimento é obrigatório");
        }

        RepresentativePreCouncil preCouncil = getPreCouncilById(id);
        preCouncil.setIsFilled(isFilled);
        return convertToDTO(preCouncilRepository.save(preCouncil));
    }

    @Transactional
    public RepresentativePreCouncilResponseDTO editSupervisorFeedback(Long id, Long supervisorFeedbackId) {
        RepresentativePreCouncil preCouncil = getPreCouncilById(id);
        SupervisorFeedback supervisorFeedback = getSupervisorFeedbackById(supervisorFeedbackId);
        preCouncil.setSupervisorFeeback(supervisorFeedback);
        return convertToDTO(preCouncilRepository.save(preCouncil));
    }

    @Transactional
    public RepresentativePreCouncilResponseDTO editAdvisorFeedback(Long id, Long advisorFeedbackId) {
        RepresentativePreCouncil preCouncil = getPreCouncilById(id);
        AdvisorFeeback advisorFeedback = getAdvisorFeedbackById(advisorFeedbackId);
        preCouncil.setAdvisorFeeback(advisorFeedback);
        return convertToDTO(preCouncilRepository.save(preCouncil));
    }

    public Page<RepresentativePreCouncilResponseDTO> findAll(Pageable pageable) {
        Page<RepresentativePreCouncil> preCouncils = preCouncilRepository.findAll(pageable);
        if (preCouncils.isEmpty()) {
            throw new NaoEncontradoException("Nenhum pré-conselho encontrado");
        }
        return preCouncils.map(this::convertToDTO);
    }

    public Page<Teacher> findAllTeachers(Pageable pageable) {
        Page<Teacher> teachers = teacherRepository.findAll(pageable);
        if (teachers.isEmpty()) {
            throw new NaoEncontradoException("Nenhum professor encontrado");
        }
        return teachers;
    }

    public RepresentativePreCouncilResponseDTO findById(Long id) {
        return convertToDTO(getPreCouncilById(id));
    }

    public Page<RepresentativePreCouncilResponseDTO> findByClasse(Long classeId, Pageable pageable) {
        if (!classeRepository.existsById(classeId)) {
            throw new NaoEncontradoException("Classe não encontrada");
        }

        Page<RepresentativePreCouncil> preCouncils = preCouncilRepository.findByClasse(classeId, pageable);

        if (preCouncils.isEmpty()) {
            throw new NaoEncontradoException("Nenhum pré-conselho encontrado para esta classe");
        }

        return preCouncils.map(this::convertToDTO);
    }

    public Page<RepresentativePreCouncilResponseDTO> search(String term, Pageable pageable) {
        String searchTerm = (term == null || term.trim().isEmpty()) ? "" : term.toLowerCase();

        Page<RepresentativePreCouncil> preCouncils = preCouncilRepository.search(
                searchTerm,
                pageable
        );

        if (preCouncils.isEmpty()) {
            throw new NaoEncontradoException("Nenhum pré-conselho encontrado com o termo: " + term);
        }

        return preCouncils.map(this::convertToDTO);
    }

    public Page<RepresentativePreCouncilResponseDTO> findByDateRange(Date startDate, Date endDate, Pageable pageable) {
        validateDates(startDate, endDate);
        Page<RepresentativePreCouncil> preCouncils = preCouncilRepository.findByDateRange(startDate, endDate, pageable);
        if (preCouncils.isEmpty()) {
            throw new NaoEncontradoException("Nenhum pré-conselho encontrado no período especificado");
        }
        return preCouncils.map(this::convertToDTO);
    }

    public Page<RepresentativePreCouncilResponseDTO> findByFillStatus(Boolean isFilled, Pageable pageable) {
        Page<RepresentativePreCouncil> preCouncils = preCouncilRepository.findByFillStatus(isFilled, pageable);
        if (preCouncils.isEmpty()) {
            throw new NaoEncontradoException("Nenhum pré-conselho encontrado com o status de preenchimento: " + isFilled);
        }
        return preCouncils.map(this::convertToDTO);
    }

    @Transactional
    public RepresentativePreCouncilResponseDTO addTeacherFeedback(Long id, Long teacherFeedbackId) {
        RepresentativePreCouncil preCouncil = getPreCouncilById(id);
        TeacherFeeback teacherFeedback = teacherFeedbackRepository.findById(teacherFeedbackId)
                .orElseThrow(() -> new NaoEncontradoException("Feedback de professor não encontrado"));

        if (preCouncil.getTeacherFeebacks().contains(teacherFeedback)) {
            throw new DadosDuplicadosException("Este feedback de professor já foi adicionado");
        }

        preCouncil.getTeacherFeebacks().add(teacherFeedback);
        return convertToDTO(preCouncilRepository.save(preCouncil));
    }

    @Transactional
    public RepresentativePreCouncilResponseDTO addAdvisorFeedback(Long id, Long advisorFeedbackId) {
        RepresentativePreCouncil preCouncil = getPreCouncilById(id);
        AdvisorFeeback advisorFeedback = getAdvisorFeedbackById(advisorFeedbackId);

        if (preCouncil.getAdvisorFeeback() != null && preCouncil.getAdvisorFeeback().getId().equals(advisorFeedbackId)) {
            throw new DadosDuplicadosException("Este feedback de orientador já foi adicionado");
        }

        preCouncil.setAdvisorFeeback(advisorFeedback);
        return convertToDTO(preCouncilRepository.save(preCouncil));
    }

    @Transactional
    public RepresentativePreCouncilResponseDTO addSupervisorFeedback(Long id, Long supervisorFeedbackId) {
        RepresentativePreCouncil preCouncil = getPreCouncilById(id);
        SupervisorFeedback supervisorFeedback = getSupervisorFeedbackById(supervisorFeedbackId);

        if (preCouncil.getSupervisorFeeback() != null && preCouncil.getSupervisorFeeback().getId().equals(supervisorFeedbackId)) {
            throw new DadosDuplicadosException("Este feedback de supervisor já foi adicionado");
        }

        preCouncil.setSupervisorFeeback(supervisorFeedback);
        return convertToDTO(preCouncilRepository.save(preCouncil));
    }

    @Transactional
    public RepresentativePreCouncilResponseDTO addItemFeedback(Long id, Long itemFeedbackId) {
        RepresentativePreCouncil preCouncil = getPreCouncilById(id);
        ItemFeedback itemFeedback = itemFeedbackRepository.findById(itemFeedbackId)
                .orElseThrow(() -> new NaoEncontradoException("Feedback de item não encontrado"));

        if (preCouncil.getItemFeedbacks().contains(itemFeedback)) {
            throw new DadosDuplicadosException("Este feedback de item já foi adicionado");
        }

        preCouncil.getItemFeedbacks().add(itemFeedback);
        return convertToDTO(preCouncilRepository.save(preCouncil));
    }

    @Transactional
    public void delete(Long id) {
        if (!preCouncilRepository.existsById(id)) {
            throw new NaoEncontradoException("Pré-conselho não encontrado");
        }
        preCouncilRepository.deleteById(id);
    }

    private RepresentativePreCouncil getPreCouncilById(Long id) {
        return preCouncilRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Pré-conselho não encontrado"));
    }

    private List<Teacher> getTeachersByIds(List<Long> teacherIds) {
        if (teacherIds == null || teacherIds.isEmpty()) {
            return new ArrayList<>();
        }
        return teacherIds.stream()
                .map(id -> teacherRepository.findById(id)
                        .orElseThrow(() -> new NaoEncontradoException("Professor não encontrado com ID: " + id)))
                .collect(Collectors.toList());
    }

    private AdvisorFeeback getAdvisorFeedbackById(Long advisorFeedbackId) {
        if (advisorFeedbackId == null) {
            return null;
        }
        return advisorFeedbackRepository.findById(advisorFeedbackId)
                .orElseThrow(() -> new NaoEncontradoException("Feedback de orientador não encontrado"));
    }

    private SupervisorFeedback getSupervisorFeedbackById(Long supervisorFeedbackId) {
        if (supervisorFeedbackId == null) {
            return null;
        }
        return supervisorFeedbackRepository.findById(supervisorFeedbackId)
                .orElseThrow(() -> new NaoEncontradoException("Feedback de supervisor não encontrado"));
    }

    private List<ItemFeedback> getItemFeedbacksByIds(List<Long> itemFeedbackIds) {
        if (itemFeedbackIds == null || itemFeedbackIds.isEmpty()) {
            return new ArrayList<>();
        }
        return itemFeedbackIds.stream()
                .map(id -> itemFeedbackRepository.findById(id)
                        .orElseThrow(() -> new NaoEncontradoException("Feedback de item não encontrado com ID: " + id)))
                .collect(Collectors.toList());
    }

    private void validateRequiredFields(RepresentativePreCouncilRequestDTO requestDTO) {
        if (requestDTO.councilId() == null) {
            throw new CamposObrigatoriosException("ID do conselho é obrigatório");
        }
        if (requestDTO.classeId() == null) {
            throw new CamposObrigatoriosException("ID da classe é obrigatório");
        }
        if (requestDTO.startDate() == null) {
            throw new CamposObrigatoriosException("Data de início é obrigatória");
        }
        if (requestDTO.endDate() == null) {
            throw new CamposObrigatoriosException("Data de fim é obrigatória");
        }
    }

    private void validateDates(Date startDate, Date endDate) {
        if (startDate != null && endDate != null && startDate.after(endDate)) {
            throw new DataInvalidaException("Data de início não pode ser após a data de fim");
        }
    }

    private RepresentativePreCouncilResponseDTO convertToDTO(RepresentativePreCouncil preCouncil) {
        return new RepresentativePreCouncilResponseDTO(
                preCouncil.getId(),
                preCouncil.getCouncil(),
                preCouncil.getCreatedAt(),
                preCouncil.getStartDate(),
                preCouncil.getEndDate(),
                preCouncil.getClasse(),
                preCouncil.getIsFilled(),
                preCouncil.getTeachers(),
                preCouncil.getAdvisorFeeback(),
                preCouncil.getSupervisorFeeback(),
                preCouncil.getTeacherFeebacks(),
                preCouncil.getItemFeedbacks()
        );
    }
}