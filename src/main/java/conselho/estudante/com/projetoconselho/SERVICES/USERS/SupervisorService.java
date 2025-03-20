package conselho.estudante.com.projetoconselho.SERVICES.USERS;


import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.USERS.SupervisorRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS.SupervisorResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Course;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Notification;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Student;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Supervisor;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Technique;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.DadosDuplicadosException;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.USERS.SupervisorRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@AllArgsConstructor
public class SupervisorService {


    private SupervisorRepository repository;


    /**
     * Cria um supervisor
     * @param supervisorRequestDTO supervisor a ser criado em formato de {@link SupervisorRequestDTO}
     * @return supervisor criado em formato de {@link SupervisorResponseDTO}
     */
    public SupervisorResponseDTO create(SupervisorRequestDTO supervisorRequestDTO) {
        Supervisor supervisor = supervisorRequestDTO.convert();
        if (repository.existsByEmail(supervisor.getEmail())) {
            throw new DadosDuplicadosException("Email já cadastrado");
        } else if (repository.existsByRegister(supervisor.getRegister())) {
            throw new DadosDuplicadosException("Cadastro já cadastrado");
        }
        return repository.save(supervisor).convert();
    }


    /**
     * Atualiza os dados do supervisor
     * @param id identificador do supervisor a ser atualizado
     * @param supervisorRequestDTO supervisor com os dados atualizados
     * @return supervisor atualizado em formato de {@link SupervisorResponseDTO}
     */
    public SupervisorResponseDTO update(Long id, SupervisorRequestDTO supervisorRequestDTO) {
        Supervisor supervisor = supervisorRequestDTO.convert();
        if (repository.existsById(id)) {
            supervisor.setId(id);
            if (repository.existsByEmail(supervisor.getEmail())) {
                throw new DadosDuplicadosException("Email já cadastrado");
            } else if (repository.existsByRegister(supervisor.getRegister())) {
                throw new DadosDuplicadosException("Cadastro já cadastrado");
            }
            return repository.save(supervisor).convert();
        }
        throw new NaoEncontradoException("Supervisor não encontrado");
    }


    /**
     * Edita o nome de um {@link Supervisor}.
     * @param id o identificador do supervisor
     * @param name o novo nome do supervisor
     * @return {@link SupervisorResponseDTO} o supervisor atualizado
     */
    public SupervisorResponseDTO editName(Long id, String name) {
        Supervisor supervisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Supervisor não encontrado"));
        supervisor.setName(name);
        return repository.save(supervisor).convert();
    }




    /**
     * Edita o email de um {@link Supervisor}.
     * @param id o identificador do supervisor
     * @param email o novo email do supervisor
     * @return {@link SupervisorResponseDTO} o supervisor atualizado
     */
    public SupervisorResponseDTO editEmail(Long id, String email) {
        Supervisor supervisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Supervisor não encontrado"));
        supervisor.setEmail(email);
        return repository.save(supervisor).convert();
    }




    /**
     * Edita o cadastro de um {@link Supervisor}.
     * @param id o identificador do supervisor
     * @param register o novo cadastro do supervisor
     * @return {@link SupervisorResponseDTO} o supervisor atualizado
     */
    public SupervisorResponseDTO editRegister(Long id, Long register) {
        Supervisor supervisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Supervisor não encontrado"));
        supervisor.setRegister(register);
        return repository.save(supervisor).convert();
    }




    /**
     * Edita a senha de um {@link Supervisor}.
     * @param id o identificador do supervisor
     * @param password a nova senha do supervisor
     * @return {@link SupervisorResponseDTO} o supervisor atualizado
     */
    public SupervisorResponseDTO editPassword(Long id, String password) {
        Supervisor supervisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Supervisor não encontrado"));
        supervisor.setPassword(password);
        return repository.save(supervisor).convert();
    }

    /**
     * Edita a senha de um supervisor
     * @param supervisor Supervisor a ser editado
     * @param password Nova senha.
     * @return Um booleano indicando se a edição foi bem sucedida
     * @author Gustavo Stinghen
     * @since 19/03/2025
     */
    public boolean editPassword(Supervisor supervisor, String password) {

        try {
            supervisor.setPassword(password);
            repository.save(supervisor);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    /**
     * Edita a imagem de perfil de um {@link Supervisor}.
     * @param id o identificador do supervisor
     * @param image a nova imagem do supervisor
     * @return {@link SupervisorResponseDTO} o supervisor atualizado
     */
    public SupervisorResponseDTO editImage(Long id, String image) {
        Supervisor supervisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Supervisor não encontrado"));
        supervisor.setImage(image);
        return repository.save(supervisor).convert();
    }

    /**
     * Retorna todos os supervisores cadastrados com paginação.
     * @param pageable as configurações de paginação
     * @return {@link Page<SupervisorResponseDTO>} a página contendo os supervisores encontrados
     * @throws NaoEncontradoException se não houver supervisores cadastrados
     */
    public Page<SupervisorResponseDTO> findSupervisors(Pageable pageable) {
        try {
            return repository.findAll(pageable)
                    .map(Supervisor::convert);
        } catch (Exception e) {
            throw new NaoEncontradoException("Supervisores não encontrados");
        }
    }




    /**
     * Retorna as notificações de um {@link Supervisor}.
     * @param id o identificador do supervisor
     * @return {@link List<Notification>} a lista de notificações do supervisor
     * @throws NaoEncontradoException se o supervisor não for encontrado
     */
    /*public List<Notification> getNotifications(Long id) {
        Supervisor supervisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Supervisor não encontrado"));
        return supervisor.getNotifications();
    }*/




    /**
     * Busca um {@link Supervisor} pelo seu identificador.
     * @param id o identificador do supervisor
     * @return {@link SupervisorResponseDTO} o supervisor encontrado
     * @throws NaoEncontradoException se o supervisor não for encontrado
     */
    public SupervisorResponseDTO findById(Long id) {
        Supervisor supervisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Supervisor não encontrado"));
        return supervisor.convert();
    }




    /**
     * Busca um {@link Supervisor} pelo email.
     * @param email o email do supervisor
     * @return {@link SupervisorResponseDTO} o supervisor encontrado
     * @throws NaoEncontradoException se o supervisor não for encontrado
     */
    public SupervisorResponseDTO findByEmail(String email) {
        Supervisor supervisor = repository.findByEmail(email);
        if (supervisor != null) {
            return supervisor.convert();
        } else {
            throw new NaoEncontradoException("Supervisor não encontrado");
        }
    }

    /**
     * Busca um {@link Supervisor} pelo email
     * @param email o email do supervisor
     * @return {@link Supervisor} o supervisor encontrado
     * Utilizado na autenticação
     * @author Gustavo Stinghen
     * @since 19/03/2025
     */
    public Supervisor findObjectSupervisor ( String email) {
        try {
            return repository.findByEmail(email);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Adiciona uma {@link Notification} a um {@link Supervisor}.
     * @param id o identificador do supervisor
     * @param notification a notificação a ser adicionada
     * @return {@link SupervisorResponseDTO} o supervisor atualizado
     * @throws NaoEncontradoException se o supervisor não for encontrado
     */
    /*public SupervisorResponseDTO addNotification(Long id, Notification notification) {
        Supervisor supervisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Supervisor não encontrado"));
        supervisor.addNotification(notification);
        return repository.save(supervisor).convert();
    }*/




    /**
     * Remove uma {@link Notification} de um {@link Supervisor}.
     * @param id o identificador do supervisor
     * @param notification a notificação a ser removida
     * @return {@link SupervisorResponseDTO} o supervisor atualizado
     * @throws NaoEncontradoException se o supervisor não for encontrado
     */
    /*public SupervisorResponseDTO removeNotification(Long id, Notification notification) {
        Supervisor supervisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Supervisor não encontrado"));
        supervisor.removeNotification(notification);
        return repository.save(supervisor).convert();
    }*/




    /**
     * Deleta um {@link Supervisor}.
     * @param id o identificador do supervisor
     * @throws NaoEncontradoException se o supervisor não for encontrado
     */
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new NaoEncontradoException("Supervisor não deletado");
        }
    }


    /**
     * Adiciona um {@link Course} a um {@link Supervisor}.
     * @param supervisorId o identificador do supervisor
     * @param course o curso a ser adicionado
     * @return {@link SupervisorResponseDTO} o supervisor atualizado
     * @throws NaoEncontradoException se o supervisor não for encontrado
     */
    public SupervisorResponseDTO addCourse(Long supervisorId, Course course) {
        Supervisor supervisor = repository.findById(supervisorId)
                .orElseThrow(() -> new NaoEncontradoException("Supervisor não encontrado"));


        supervisor.addCourse(course);


        return repository.save(supervisor).convert();
    }


    /**
     * Remove um {@link Course} de um {@link Supervisor}.
     * @param supervisorId o identificador do supervisor
     * @param course o curso a ser removido
     * @return {@link SupervisorResponseDTO} o supervisor atualizado
     * @throws NaoEncontradoException se o supervisor não for encontrado ou não estiver associado ao curso
     */
    public SupervisorResponseDTO removeCourse(Long supervisorId, Course course) {
        Supervisor supervisor = repository.findById(supervisorId)
                .orElseThrow(() -> new NaoEncontradoException("Supervisor não encontrado"));


        supervisor.removeCourse(course);


        return repository.save(supervisor).convert();
    }

    /**
     * Filtra os supervisores por curso.
     *
     * @param courseId o identificador do curso
     * @param pageable as configurações de paginação
     * @return {@link Page<SupervisorResponseDTO>} os supervisores do curso especificado
     * @throws NaoEncontradoException se nenhum supervisor for encontrado
     */
    public Page<SupervisorResponseDTO> filterByCourse(Long courseId, Pageable pageable) {
        Page<Supervisor> supervisors = repository.findByCourses_Id(courseId, pageable);

        if (supervisors.isEmpty()) {
            throw new NaoEncontradoException("Nenhum supervisor encontrado para este curso.");
        }

        return supervisors.map(Supervisor::convert);
    }

    /**
     * Filtra os supervisores por turma.
     *
     * @param classId o identificador da turma
     * @param pageable as configurações de paginação
     * @return {@link Page<SupervisorResponseDTO>} os supervisores da turma especificada
     * @throws NaoEncontradoException se nenhum supervisor for encontrado
     */
    public Page<SupervisorResponseDTO> filterByClass(Long classId, Pageable pageable) {
        Page<Supervisor> supervisors = repository.findByClasses_Id(classId, pageable);

        if (supervisors.isEmpty()) {
            throw new NaoEncontradoException("Nenhum supervisor encontrado para esta turma.");
        }

        return supervisors.map(Supervisor::convert);
    }

    /**
     * Realiza uma pesquisa inteligente baseada em múltiplos critérios.
     *
     * @param searchTerm o termo de pesquisa (nome, email, matrícula, etc.)
     * @param pageable as configurações de paginação
     * @return {@link Page<SupervisorResponseDTO>} os supervisores que correspondem ao critério de pesquisa
     * @throws NaoEncontradoException se nenhum supervisor for encontrado
     */
    public Page<SupervisorResponseDTO> intelligentSearch(String searchTerm, Pageable pageable) {
        Page<Supervisor> supervisors = repository.searchByMultipleFields(searchTerm, pageable);

        if (supervisors.isEmpty()) {
            throw new NaoEncontradoException("Nenhum supervisor encontrado para o critério de pesquisa fornecido.");
        }

        return supervisors.map(Supervisor::convert);
    }
}

