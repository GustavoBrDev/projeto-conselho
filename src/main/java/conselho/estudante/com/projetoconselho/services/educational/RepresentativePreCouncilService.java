package conselho.estudante.com.projetoconselho.services.educational;

import conselho.estudante.com.projetoconselho.models.dto.request.EDUCATIONAL.RepresentativePreCouncilRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.educational.RepresentativePreCouncilResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.administration.Classe;
import conselho.estudante.com.projetoconselho.models.entity.educational.*;
import conselho.estudante.com.projetoconselho.models.entity.users.Teacher;
import conselho.estudante.com.projetoconselho.models.exceptions.*;
import conselho.estudante.com.projetoconselho.repositories.administration.ClasseRepository;
import conselho.estudante.com.projetoconselho.repositories.educational.*;
import conselho.estudante.com.projetoconselho.repositories.users.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Serviço para operações relacionadas a pré-conselhos de representantes.
 *
 * Esta classe fornece todas as operações CRUD para pré-conselhos de representantes,
 * incluindo criação, atualização, consulta e exclusão, além de operações específicas
 * como gestão de feedbacks e filtros avançados.
 *
 * @author Alex Zastrow
 *
 * @see RepresentativePreCouncil
 * @see RepresentativePreCouncilRequestDTO
 * @see RepresentativePreCouncilResponseDTO
 */
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
     * Cria um novo pré-conselho de representantes.
     *
     * @param requestDTO DTO contendo os dados para criação do pré-conselho
     * @return DTO com os dados do pré-conselho criado
     * @throws CamposObrigatoriosException se algum campo obrigatório não for informado
     * @throws DataInvalidaException se as datas informadas forem inválidas
     * @throws NaoEncontradoException se algum recurso relacionado não for encontrado
     */
    @Transactional
    public RepresentativePreCouncilResponseDTO create(RepresentativePreCouncilRequestDTO requestDTO) {
        validateRequiredFields(requestDTO);
        validateDates(requestDTO.startDate(), requestDTO.endDate());


        Council council = councilRepository.findById(requestDTO.councilId())
                .orElseThrow(() -> new NaoEncontradoException("Conselho não encontrado"));


        Classe classe = classeRepository.findById(requestDTO.classeId())
                .orElseThrow(() -> new NaoEncontradoException("Classe não encontrada"));


        RepresentativePreCouncil preCouncil = RepresentativePreCouncil.builder()
                .council(council)
                .classe(classe)
                .startDate(requestDTO.startDate())
                .endDate(requestDTO.endDate())
                .teachers(requestDTO.teachers())
                .build();


        return convertToDTO(preCouncilRepository.save(preCouncil));
    }


    /**
     * Atualiza um pré-conselho existente.
     *
     * @param id ID do pré-conselho a ser atualizado
     * @param requestDTO DTO com os novos dados do pré-conselho
     * @return DTO com os dados atualizados
     * @throws CamposObrigatoriosException se algum campo obrigatório não for informado
     * @throws DataInvalidaException se as datas informadas forem inválidas
     * @throws NaoEncontradoException se o pré-conselho ou recursos relacionados não forem encontrados
     */
    @Transactional
    public RepresentativePreCouncilResponseDTO update(Long id, RepresentativePreCouncilRequestDTO requestDTO) {
        validateRequiredFields(requestDTO);
        validateDates(requestDTO.startDate(), requestDTO.endDate());


        RepresentativePreCouncil preCouncil = getPreCouncilById(id);


        Council council = councilRepository.findById(requestDTO.councilId())
                .orElseThrow(() -> new NaoEncontradoException("Conselho não encontrado"));


        Classe classe = classeRepository.findById(requestDTO.classeId())
                .orElseThrow(() -> new NaoEncontradoException("Classe não encontrada"));


        RepresentativePreCouncil preCouncilUpdated = RepresentativePreCouncil.builder()
                .id(preCouncil.getId())
                .council(council)
                .classe(classe)
                .startDate(requestDTO.startDate())
                .endDate(requestDTO.endDate())
                .teachers(requestDTO.teachers())
                .build();


        return convertToDTO(preCouncilRepository.save(preCouncilUpdated));
    }


    /**
     * Atualiza a data de início de um pré-conselho.
     *
     * @param id ID do pré-conselho
     * @param startDate Nova data de início
     * @return DTO com os dados atualizados
     * @throws CamposObrigatoriosException se a data não for informada
     * @throws DataInvalidaException se a nova data for após a data de fim
     * @throws NaoEncontradoException se o pré-conselho não for encontrado
     */
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


    /**
     * Atualiza a data de fim de um pré-conselho.
     *
     * @param id ID do pré-conselho
     * @param endDate Nova data de fim
     * @return DTO com os dados atualizados
     * @throws CamposObrigatoriosException se a data não for informada
     * @throws DataInvalidaException se a nova data for anterior à data de início
     * @throws NaoEncontradoException se o pré-conselho não for encontrado
     */
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


    /**
     * Atualiza o status de preenchimento de um pré-conselho.
     *
     * @param id ID do pré-conselho
     * @param isFilled Novo status de preenchimento
     * @return DTO com os dados atualizados
     * @throws CamposObrigatoriosException se o status não for informado
     * @throws NaoEncontradoException se o pré-conselho não for encontrado
     */
    @Transactional
    public RepresentativePreCouncilResponseDTO editIsFilled(Long id, Boolean isFilled) {
        if (isFilled == null) {
            throw new CamposObrigatoriosException("Status de preenchimento é obrigatório");
        }


        RepresentativePreCouncil preCouncil = getPreCouncilById(id);
        preCouncil.setIsFilled(isFilled);
        return convertToDTO(preCouncilRepository.save(preCouncil));
    }


    /**
     * Atualiza o feedback do supervisor associado ao pré-conselho.
     *
     * @param id ID do pré-conselho
     * @param supervisorFeedbackId ID do novo feedback de supervisor
     * @return DTO com os dados atualizados
     * @throws NaoEncontradoException se o pré-conselho ou feedback não forem encontrados
     * @throws DadosDuplicadosException se o feedback já estiver associado ao pré-conselho
     */
    @Transactional
    public RepresentativePreCouncilResponseDTO editSupervisorFeedback(Long id, Long supervisorFeedbackId) {
        RepresentativePreCouncil preCouncil = getPreCouncilById(id);
        SupervisorFeedback supervisorFeedback = getSupervisorFeedbackById(supervisorFeedbackId);
        preCouncil.setSupervisorFeeback(supervisorFeedback);
        return convertToDTO(preCouncilRepository.save(preCouncil));
    }


    /**
     * Atualiza o feedback do orientador associado ao pré-conselho.
     *
     * @param id ID do pré-conselho
     * @param advisorFeedbackId ID do novo feedback de orientador
     * @return DTO com os dados atualizados
     * @throws NaoEncontradoException se o pré-conselho ou feedback não forem encontrados
     * @throws DadosDuplicadosException se o feedback já estiver associado ao pré-conselho
     */
    @Transactional
    public RepresentativePreCouncilResponseDTO editAdvisorFeedback(Long id, Long advisorFeedbackId) {
        RepresentativePreCouncil preCouncil = getPreCouncilById(id);
        AdvisorFeeback advisorFeedback = getAdvisorFeedbackById(advisorFeedbackId);
        preCouncil.setAdvisorFeeback(advisorFeedback);
        return convertToDTO(preCouncilRepository.save(preCouncil));
    }


    /**
     * Retorna todos os pré-conselhos com paginação.
     *
     * @param pageable Configurações de paginação
     * @return Página de DTOs de pré-conselhos
     * @throws NaoEncontradoException se nenhum pré-conselho for encontrado
     */
    public Page<RepresentativePreCouncilResponseDTO> findAll(Pageable pageable) {
        Page<RepresentativePreCouncil> preCouncils = preCouncilRepository.findAll(pageable);
        if (preCouncils.isEmpty()) {
            throw new NaoEncontradoException("Nenhum pré-conselho encontrado");
        }
        return preCouncils.map(this::convertToDTO);
    }


    /**
     * Retorna todos os professores com paginação.
     *
     * @param pageable Configurações de paginação
     * @return Página de professores
     * @throws NaoEncontradoException se nenhum professor for encontrado
     */
    public Page<Teacher> findAllTeachers(Pageable pageable) {
        Page<Teacher> teachers = teacherRepository.findAll(pageable);
        if (teachers.isEmpty()) {
            throw new NaoEncontradoException("Nenhum professor encontrado");
        }
        return teachers;
    }


    /**
     * Busca um pré-conselho pelo ID.
     *
     * @param id ID do pré-conselho
     * @return DTO com os dados do pré-conselho
     * @throws NaoEncontradoException se o pré-conselho não for encontrado
     */
    public RepresentativePreCouncilResponseDTO findById(Long id) {
        return convertToDTO(getPreCouncilById(id));
    }


    /**
     * Filtra pré-conselhos por classe.
     *
     * @param classeId ID da classe
     * @param pageable Configurações de paginação
     * @return Página de DTOs de pré-conselhos da classe especificada
     * @throws NaoEncontradoException se a classe não existir ou não houver pré-conselhos
     */
    public Page<RepresentativePreCouncilResponseDTO> findByClasse(Long classeId, Pageable pageable) {
       /* if (!classeRepository.existsById(classeId)) {
            throw new NaoEncontradoException("Classe não encontrada");
        }


        Page<RepresentativePreCouncil> preCouncils = preCouncilRepository.findByClasse(classeId, pageable);


        if (preCouncils.isEmpty()) {
            throw new NaoEncontradoException("Nenhum pré-conselho encontrado para esta classe");
        }


        return preCouncils.map(this::convertToDTO);*/
        return null;
    }


    /**
     * Busca pré-conselhos por termo de pesquisa.
     *
     * @param term Termo para busca (pode ser nome da classe, conselho ou professores)
     * @param pageable Configurações de paginação
     * @return Página de DTOs de pré-conselhos encontrados
     * @throws NaoEncontradoException se nenhum pré-conselho for encontrado
     */
    public Page<RepresentativePreCouncilResponseDTO> search(String term, Pageable pageable) {
        String searchTerm = (term == null || term.trim().isEmpty()) ? "" : term.toLowerCase();


       /* Page<RepresentativePreCouncil> preCouncils = preCouncilRepository.search(
                searchTerm,
                pageable
        );


        if (preCouncils.isEmpty()) {
            throw new NaoEncontradoException("Nenhum pré-conselho encontrado com o termo: " + term);
        }


        return preCouncils.map(this::convertToDTO);*/
        return null;
    }


    /**
     * Filtra pré-conselhos por intervalo de datas.
     *
     * @param startDate Data de início do intervalo
     * @param endDate Data de fim do intervalo
     * @param pageable Configurações de paginação
     * @return Página de DTOs de pré-conselhos no intervalo especificado
     * @throws DataInvalidaException se as datas forem inválidas
     * @throws NaoEncontradoException se nenhum pré-conselho for encontrado no período
     */
    public Page<RepresentativePreCouncilResponseDTO> findByDateRange(Date startDate, Date endDate, Pageable pageable) {
        /*validateDates(startDate, endDate);
        Page<RepresentativePreCouncil> preCouncils = preCouncilRepository.findByDateRange(startDate, endDate, pageable);
        if (preCouncils.isEmpty()) {
            throw new NaoEncontradoException("Nenhum pré-conselho encontrado no período especificado");
        }
        return preCouncils.map(this::convertToDTO);*/
        return null;
    }


    /**
     * Filtra pré-conselhos por status de preenchimento.
     *
     * @param isFilled Status de preenchimento para filtro
     * @param pageable Configurações de paginação
     * @return Página de DTOs de pré-conselhos com o status especificado
     * @throws NaoEncontradoException se nenhum pré-conselho for encontrado com o status
     */
    public Page<RepresentativePreCouncilResponseDTO> findByFillStatus(Boolean isFilled, Pageable pageable) {
        Page<RepresentativePreCouncil> preCouncils = preCouncilRepository.findByFillStatus(isFilled, pageable);
        if (preCouncils.isEmpty()) {
            throw new NaoEncontradoException("Nenhum pré-conselho encontrado com o status de preenchimento: " + isFilled);
        }
        return preCouncils.map(this::convertToDTO);
    }


    /**
     * Adiciona um feedback de professor ao pré-conselho.
     *
     * @param id ID do pré-conselho
     * @param teacherFeedbackId ID do feedback a ser adicionado
     * @return DTO com os dados atualizados
     * @throws NaoEncontradoException se o pré-conselho ou feedback não forem encontrados
     * @throws DadosDuplicadosException se o feedback já estiver associado ao pré-conselho
     */
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


    /**
     * Adiciona um feedback de orientador ao pré-conselho.
     *
     * @param id ID do pré-conselho
     * @param advisorFeedbackId ID do feedback a ser adicionado
     * @return DTO com os dados atualizados
     * @throws NaoEncontradoException se o pré-conselho ou feedback não forem encontrados
     * @throws DadosDuplicadosException se o feedback já estiver associado ao pré-conselho
     */
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


    /**
     * Adiciona um feedback de supervisor ao pré-conselho.
     *
     * @param id ID do pré-conselho
     * @param supervisorFeedbackId ID do feedback a ser adicionado
     * @return DTO com os dados atualizados
     * @throws NaoEncontradoException se o pré-conselho ou feedback não forem encontrados
     * @throws DadosDuplicadosException se o feedback já estiver associado ao pré-conselho
     */
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


    /**
     * Adiciona um feedback de item ao pré-conselho.
     *
     * @param id ID do pré-conselho
     * @param itemFeedbackId ID do feedback a ser adicionado
     * @return DTO com os dados atualizados
     * @throws NaoEncontradoException se o pré-conselho ou feedback não forem encontrados
     * @throws DadosDuplicadosException se o feedback já estiver associado ao pré-conselho
     */
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


    /**
     * Remove um pré-conselho.
     *
     * @param id ID do pré-conselho a ser removido
     * @throws NaoEncontradoException se o pré-conselho não for encontrado
     */
    @Transactional
    public void delete(Long id) {
        if (!preCouncilRepository.existsById(id)) {
            throw new NaoEncontradoException("Pré-conselho não encontrado");
        }
        preCouncilRepository.deleteById(id);
    }


    /**
     * Recupera um pré-conselho pelo ID.
     *
     * @param id ID do pré-conselho
     * @return Entidade RepresentativePreCouncil
     * @throws NaoEncontradoException se o pré-conselho não for encontrado
     */
    private RepresentativePreCouncil getPreCouncilById(Long id) {
        return preCouncilRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Pré-conselho não encontrado"));
    }


    /**
     * Obtém professores pelos seus IDs.
     *
     * @param teacherIds Lista de IDs de professores
     * @return Lista de professores encontrados
     * @throws NaoEncontradoException se algum professor não for encontrado
     */
    private List<Teacher> getTeachersByIds(List<Long> teacherIds) {
        if (teacherIds == null || teacherIds.isEmpty()) {
            return new ArrayList<>();
        }
        return teacherIds.stream()
                .map(id -> teacherRepository.findById(id)
                        .orElseThrow(() -> new NaoEncontradoException("Professor não encontrado com ID: " + id)))
                .collect(Collectors.toList());
    }


    /**
     * Obtém feedback de orientador pelo ID.
     *
     * @param advisorFeedbackId ID do feedback
     * @return Entidade AdvisorFeedback ou null se o ID for null
     * @throws NaoEncontradoException se o feedback não for encontrado
     */
    private AdvisorFeeback getAdvisorFeedbackById(Long advisorFeedbackId) {
        if (advisorFeedbackId == null) {
            return null;
        }
        return advisorFeedbackRepository.findById(advisorFeedbackId)
                .orElseThrow(() -> new NaoEncontradoException("Feedback de orientador não encontrado"));
    }


    /**
     * Obtém feedback de supervisor pelo ID.
     *
     * @param supervisorFeedbackId ID do feedback
     * @return Entidade SupervisorFeedback ou null se o ID for null
     * @throws NaoEncontradoException se o feedback não for encontrado
     */
    private SupervisorFeedback getSupervisorFeedbackById(Long supervisorFeedbackId) {
        if (supervisorFeedbackId == null) {
            return null;
        }
        return supervisorFeedbackRepository.findById(supervisorFeedbackId)
                .orElseThrow(() -> new NaoEncontradoException("Feedback de supervisor não encontrado"));
    }


    /**
     * Obtém feedbacks de itens pelos seus IDs.
     *
     * @param itemFeedbackIds Lista de IDs de feedbacks de itens
     * @return Lista de feedbacks de itens encontrados
     * @throws NaoEncontradoException se algum feedback não for encontrado
     */
    private List<ItemFeedback> getItemFeedbacksByIds(List<Long> itemFeedbackIds) {
        if (itemFeedbackIds == null || itemFeedbackIds.isEmpty()) {
            return new ArrayList<>();
        }
        return itemFeedbackIds.stream()
                .map(id -> itemFeedbackRepository.findById(id)
                        .orElseThrow(() -> new NaoEncontradoException("Feedback de item não encontrado com ID: " + id)))
                .collect(Collectors.toList());
    }


    /**
     * Valida os campos obrigatórios do DTO de requisição.
     *
     * @param requestDTO DTO a ser validado
     * @throws CamposObrigatoriosException se algum campo obrigatório não for informado
     */
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


    /**
     * Valida um intervalo de datas.
     *
     * @param startDate Data de início
     * @param endDate Data de fim
     * @throws DataInvalidaException se a data de início for após a data de fim
     */
    private void validateDates(Date startDate, Date endDate) {
        if (startDate != null && endDate != null && startDate.after(endDate)) {
            throw new DataInvalidaException("Data de início não pode ser após a data de fim");
        }
    }


    /**
     * Converte a entidade RepresentativePreCouncil para DTO.
     *
     * @param preCouncil Entidade a ser convertida
     * @return DTO com os dados da entidade
     */
    private RepresentativePreCouncilResponseDTO convertToDTO(RepresentativePreCouncil preCouncil) {
       return null;
    }
}
