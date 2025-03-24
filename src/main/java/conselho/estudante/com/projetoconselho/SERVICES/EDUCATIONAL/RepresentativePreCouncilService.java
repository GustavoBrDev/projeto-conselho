package conselho.estudante.com.projetoconselho.SERVICES.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.EDUCATIONAL.RepresentativePreCouncilRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL.RepresentativePreCouncilResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.*;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.DadosDuplicadosException;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.ADMINISTRATION.ClasseRepository;
import conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL.*;
import conselho.estudante.com.projetoconselho.REPOSITORIES.USERS.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    /**
     * Cria um novo pré-conselho de representantes
     */
    public RepresentativePreCouncilResponseDTO create(RepresentativePreCouncilRequestDTO requestDTO) {
        Council council = councilRepository.findById(requestDTO.councilId())
                .orElseThrow(() -> new NaoEncontradoException("Conselho não encontrado"));

        Classe classe = classeRepository.findById(requestDTO.classeId())
                .orElseThrow(() -> new NaoEncontradoException("Classe não encontrada"));

        List<Teacher> teachers = new ArrayList<>();
        if (requestDTO.teacherIds() != null) {
            teachers = requestDTO.teacherIds().stream()
                    .map(id -> teacherRepository.findById(id)
                            .orElseThrow(() -> new NaoEncontradoException("Professor não encontrado")))
                    .collect(Collectors.toList());
        }

        AdvisorFeeback advisorFeedback = requestDTO.advisorFeedbackId() != null ?
                advisorFeedbackRepository.findById(requestDTO.advisorFeedbackId())
                        .orElseThrow(() -> new NaoEncontradoException("Feedback de orientador não encontrado")) :
                null;

        SupervisorFeedback supervisorFeedback = requestDTO.supervisorFeedbackId() != null ?
                supervisorFeedbackRepository.findById(requestDTO.supervisorFeedbackId())
                        .orElseThrow(() -> new NaoEncontradoException("Feedback de supervisor não encontrado")) :
                null;

        List<ItemFeedback> itemFeedbacks = new ArrayList<>();
        if (requestDTO.itemFeedbackIds() != null) {
            itemFeedbacks = requestDTO.itemFeedbackIds().stream()
                    .map(id -> itemFeedbackRepository.findById(id)
                            .orElseThrow(() -> new NaoEncontradoException("Feedback de item não encontrado")))
                    .collect(Collectors.toList());
        }

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

    /**
     * Atualiza um pré-conselho existente
     */
    public RepresentativePreCouncilResponseDTO update(Long id, RepresentativePreCouncilRequestDTO requestDTO) {
        RepresentativePreCouncil preCouncil = getPreCouncilById(id);

        Council council = councilRepository.findById(requestDTO.councilId())
                .orElseThrow(() -> new NaoEncontradoException("Conselho não encontrado"));

        Classe classe = classeRepository.findById(requestDTO.classeId())
                .orElseThrow(() -> new NaoEncontradoException("Classe não encontrada"));

        List<Teacher> teachers = new ArrayList<>();
        if (requestDTO.teacherIds() != null) {
            teachers = requestDTO.teacherIds().stream()
                    .map(teacherId -> teacherRepository.findById(teacherId)
                            .orElseThrow(() -> new NaoEncontradoException("Professor não encontrado")))
                    .collect(Collectors.toList());
        }

        AdvisorFeeback advisorFeedback = requestDTO.advisorFeedbackId() != null ?
                advisorFeedbackRepository.findById(requestDTO.advisorFeedbackId())
                        .orElseThrow(() -> new NaoEncontradoException("Feedback de orientador não encontrado")) :
                null;

        SupervisorFeedback supervisorFeedback = requestDTO.supervisorFeedbackId() != null ?
                supervisorFeedbackRepository.findById(requestDTO.supervisorFeedbackId())
                        .orElseThrow(() -> new NaoEncontradoException("Feedback de supervisor não encontrado")) :
                null;

        List<ItemFeedback> itemFeedbacks = new ArrayList<>();
        if (requestDTO.itemFeedbackIds() != null) {
            itemFeedbacks = requestDTO.itemFeedbackIds().stream()
                    .map(itemId -> itemFeedbackRepository.findById(itemId)
                            .orElseThrow(() -> new NaoEncontradoException("Feedback de item não encontrado")))
                    .collect(Collectors.toList());
        }

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

    /**
     * Edita a data de início do pré-conselho
     */
    public RepresentativePreCouncilResponseDTO editStartDate(Long id, Date startDate) {
        RepresentativePreCouncil preCouncil = getPreCouncilById(id);
        preCouncil.setStartDate(startDate);
        return convertToDTO(preCouncilRepository.save(preCouncil));
    }

    /**
     * Edita a data de fim do pré-conselho
     */
    public RepresentativePreCouncilResponseDTO editEndDate(Long id, Date endDate) {
        RepresentativePreCouncil preCouncil = getPreCouncilById(id);
        preCouncil.setEndDate(endDate);
        return convertToDTO(preCouncilRepository.save(preCouncil));
    }

    /**
     * Atualiza o status de preenchimento do pré-conselho
     */
    public RepresentativePreCouncilResponseDTO editIsFilled(Long id, Boolean isFilled) {
        RepresentativePreCouncil preCouncil = getPreCouncilById(id);
        preCouncil.setIsFilled(isFilled);
        return convertToDTO(preCouncilRepository.save(preCouncil));
    }

    /**
     * Edita o supervisor associado ao pré-conselho
     */
    public RepresentativePreCouncilResponseDTO editSupervisorFeedback(Long id, Long supervisorFeedbackId) {
        RepresentativePreCouncil preCouncil = getPreCouncilById(id);

        SupervisorFeedback supervisorFeedback = supervisorFeedbackId != null ?
                supervisorFeedbackRepository.findById(supervisorFeedbackId)
                        .orElseThrow(() -> new NaoEncontradoException("Feedback de supervisor não encontrado")) :
                null;

        preCouncil.setSupervisorFeeback(supervisorFeedback);
        return convertToDTO(preCouncilRepository.save(preCouncil));
    }

    /**
     * Edita o orientador associado ao pré-conselho
     */
    public RepresentativePreCouncilResponseDTO editAdvisorFeedback(Long id, Long advisorFeedbackId) {
        RepresentativePreCouncil preCouncil = getPreCouncilById(id);

        AdvisorFeeback advisorFeedback = advisorFeedbackId != null ?
                advisorFeedbackRepository.findById(advisorFeedbackId)
                        .orElseThrow(() -> new NaoEncontradoException("Feedback de orientador não encontrado")) :
                null;

        preCouncil.setAdvisorFeeback(advisorFeedback);
        return convertToDTO(preCouncilRepository.save(preCouncil));
    }

    /**
     * Lista todos os pré-conselhos com paginação
     */
    public Page<RepresentativePreCouncilResponseDTO> findAll(Pageable pageable) {
        Page<RepresentativePreCouncil> preCouncils = preCouncilRepository.findAll(pageable);
        if (preCouncils.isEmpty()) {
            throw new NaoEncontradoException("Nenhum pré-conselho encontrado");
        }
        return preCouncils.map(this::convertToDTO);
    }

    /**
     * Busca um pré-conselho por ID
     */
    public RepresentativePreCouncilResponseDTO findById(Long id) {
        return convertToDTO(getPreCouncilById(id));
    }

    /**
     * Adiciona feedback de professor ao pré-conselho
     */
    public RepresentativePreCouncilResponseDTO addTeacherFeedback(Long id, Long teacherFeedbackId) {
        RepresentativePreCouncil preCouncil = getPreCouncilById(id);
        TeacherFeeback teacherFeedback = teacherFeedbackRepository.findById(teacherFeedbackId)
                .orElseThrow(() -> new NaoEncontradoException("Feedback de professor não encontrado"));

        preCouncil.getTeacherFeebacks().add(teacherFeedback);
        return convertToDTO(preCouncilRepository.save(preCouncil));
    }

    /**
     * Adiciona feedback de orientador ao pré-conselho
     */
    public RepresentativePreCouncilResponseDTO addAdvisorFeedback(Long id, Long advisorFeedbackId) {
        RepresentativePreCouncil preCouncil = getPreCouncilById(id);
        AdvisorFeeback advisorFeedback = advisorFeedbackRepository.findById(advisorFeedbackId)
                .orElseThrow(() -> new NaoEncontradoException("Feedback de orientador não encontrado"));

        preCouncil.setAdvisorFeeback(advisorFeedback);
        return convertToDTO(preCouncilRepository.save(preCouncil));
    }

    /**
     * Adiciona feedback de supervisor ao pré-conselho
     */
    public RepresentativePreCouncilResponseDTO addSupervisorFeedback(Long id, Long supervisorFeedbackId) {
        RepresentativePreCouncil preCouncil = getPreCouncilById(id);
        SupervisorFeedback supervisorFeedback = supervisorFeedbackRepository.findById(supervisorFeedbackId)
                .orElseThrow(() -> new NaoEncontradoException("Feedback de supervisor não encontrado"));

        preCouncil.setSupervisorFeeback(supervisorFeedback);
        return convertToDTO(preCouncilRepository.save(preCouncil));
    }

    /**
     * Adiciona feedback de item ao pré-conselho
     */
    public RepresentativePreCouncilResponseDTO addItemFeedback(Long id, Long itemFeedbackId) {
        RepresentativePreCouncil preCouncil = getPreCouncilById(id);
        ItemFeedback itemFeedback = itemFeedbackRepository.findById(itemFeedbackId)
                .orElseThrow(() -> new NaoEncontradoException("Feedback de item não encontrado"));

        preCouncil.getItemFeedbacks().add(itemFeedback);
        return convertToDTO(preCouncilRepository.save(preCouncil));
    }

    /**
     * Remove um pré-conselho
     */
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