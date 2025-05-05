package conselho.estudante.com.projetoconselho.services.users;

import conselho.estudante.com.projetoconselho.models.dto.request.users.AdvisorRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.users.StudentResponseDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.users.AdvisorResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.administration.Notification;
import conselho.estudante.com.projetoconselho.models.entity.logs.AddItem;
import conselho.estudante.com.projetoconselho.models.entity.logs.ChangeItem;
import conselho.estudante.com.projetoconselho.models.entity.logs.EditableItem;
import conselho.estudante.com.projetoconselho.models.entity.users.Advisor;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import conselho.estudante.com.projetoconselho.models.exceptions.DadosDuplicadosException;
import conselho.estudante.com.projetoconselho.models.exceptions.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.repositories.users.AdvisorRepository;
import conselho.estudante.com.projetoconselho.services.EmailService;
import conselho.estudante.com.projetoconselho.services.logs.UserLogsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Serviço para gerenciar operações relacionadas à entidade {@link Advisor}.
 *
 * @author Gustavo Stinghen
 * @since 23/04/2025
 * @see Advisor
 * @see AdvisorRequestDTO
 * @see AdvisorResponseDTO
 * @see UserLogsService
 */

@Service
@AllArgsConstructor
public class AdvisorService {

    private AdvisorRepository repository;
    private EmailService emailService;
    private UserLogsService logsService;
    private static final int passwordLength = 8;

    /**
     * Cria uma nova técnica pedagogica.
     *
     * @param AdvisorRequestDTO Dados da técnica a serem criados.
     * @param actor Usuário que criou a técnica.
     * @return DTO da resposta contendo a técnica criada.
     * @throws DadosDuplicadosException se o email ou registro já existirem.
     */
    public AdvisorResponseDTO create(AdvisorRequestDTO AdvisorRequestDTO, User actor) {
        Advisor Advisor = AdvisorRequestDTO.convert();
        if(repository.existsByEmail(Advisor.getEmail())) {
            throw new DadosDuplicadosException("Email ja cadastrado");
        } else if (repository.existsByRegister(Advisor.getRegister())) {
            throw new DadosDuplicadosException("Registro ja cadastrado");
        } else {
            Advisor.setPassword(generateRandomPassword());
            Advisor.setCreatedAt( new Date());
            emailService.sendWelcomeEmail(Advisor.getEmail(), Advisor.getPassword());
            Advisor = repository.save(Advisor);
            logsService.create(actor, Advisor, "create");
            return Advisor.convert();
        }
    }

    /**
     * Método auxiliar para gerar uma senha aleatória com o tamanho especificado.
     * @return uma String com a senha gerada
     * @see SecureRandom
     */
    private String generateRandomPassword() {
        final String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
        SecureRandom random = new SecureRandom();
        StringBuilder senha = new StringBuilder();
        for (int i = 0; i < passwordLength; i++) {
            int index = random.nextInt(caracteres.length());
            senha.append(caracteres.charAt(index));
        }
        return senha.toString();
    }

    /**
     * Atualiza um orientador  pedagogica existente.
     *
     * @param id ID da técnica a ser atualizada.
     * @param AdvisorRequestDTO Novos dados da técnica.
     * @return DTO da resposta contendo a técnica atualizada.
     * @throws DadosDuplicadosException se o email ou registro já existirem ou se a técnica não for encontrado.
     */
    public AdvisorResponseDTO update(Long id, AdvisorRequestDTO AdvisorRequestDTO, User actor) {
        Advisor Advisor = AdvisorRequestDTO.convert();
        if (repository.existsById(id)) {
            Advisor.setId(id);
            if (repository.existsByEmail(Advisor.getEmail())) {

                if ( ! repository.findByEmail(Advisor.getEmail()).getId().equals(id) ) {
                    throw new DadosDuplicadosException("Email ja cadastrado");
                }

            } else if (repository.existsByRegister(Advisor.getRegister())) {

                if ( ! repository.findByRegister(Advisor.getRegister()).getId().equals(id) ) {
                    throw new DadosDuplicadosException("Registro ja cadastrado");
                }
            }

            Advisor.setCreatedAt( repository.findById(id).get().getCreatedAt() );
            logsService.create( actor, Advisor,getEditableItems(repository.findById(id).get(), Advisor), "update" );
            return repository.save(Advisor).convert();
        }
        throw new NaoEncontradoException("Orientador nao encontrado");
    }

    /**
     * Metodo auxiliar para obter os itens editaveis de um orientador
     * @param oldAdvisor o objeto tecnica antigo
     * @param newAdvisor o objeto tecnica novo
     * @return uma lista com os itens editaveis
     */
    public List<EditableItem> getEditableItems( Advisor oldAdvisor, Advisor newAdvisor) {

        List<EditableItem> changes = new ArrayList<>();

        if (! oldAdvisor.getName().equals(newAdvisor.getName())) {
            changes.add(new ChangeItem("name", (Object) oldAdvisor.getName(), (Object) newAdvisor.getName()));
        }

        if (! oldAdvisor.getEmail().equals(newAdvisor.getEmail())) {
            changes.add(new ChangeItem("email", (Object) oldAdvisor.getEmail(), (Object) newAdvisor.getEmail()));
        }

        if (! oldAdvisor.getRegister().equals(newAdvisor.getRegister())) {
            changes.add(new ChangeItem("register", (Object) oldAdvisor.getRegister(), (Object) newAdvisor.getRegister()));
        }

        if (! oldAdvisor.getPassword().equals(newAdvisor.getPassword())) {
            changes.add(new ChangeItem("password", (Object) oldAdvisor.getPassword(), (Object) newAdvisor.getPassword()));
        }

        if ( ! oldAdvisor.getImage().equals( newAdvisor.getImage() ) ) {
            changes.add(new ChangeItem("image", (Object) oldAdvisor.getImage(), (Object) newAdvisor.getImage()));
        }

        return changes;
    }

    /**
     * Edita o nome de um orientador específico.
     *
     * @param id ID da técnica.
     * @param name Novo nome.
     * @param actor Usuário que editou a técnica.
     * @return DTO da resposta contendo a técnica atualizada.
     */
    public AdvisorResponseDTO editName(Long id, String name, User actor) {
        Advisor Advisor = repository.findById(id).get();
        logsService.create(actor, Advisor, Collections.singletonList(new ChangeItem("name", (Object) Advisor.getName(), (Object) name)), "update");
        Advisor.setName(name);
        return repository.save(Advisor).convert();
    }

    /**
     * Edita o email de um orientador específico.
     *
     * @param id ID da técnica.
     * @param email Novo email.
     * @param actor Usuário que editou a técnica.
     * @return DTO da resposta contendo a técnica atualizada.
     */
    public AdvisorResponseDTO editEmail(Long id, String email, User actor) {
        Advisor Advisor = repository.findById(id).get();
        logsService.create(actor, Advisor, Collections.singletonList(new ChangeItem("email", (Object) Advisor.getEmail(), (Object) email)), "update");
        Advisor.setEmail(email);
        return repository.save(Advisor).convert();
    }

    /**
     * Edita o número de registro de um orientador específico.
     *
     * @param id ID da técnica.
     * @param register Novo número de registro.
     * @param actor Usuário que editou a técnica.
     * @return DTO da resposta contendo a técnica atualizada.
     */
    public AdvisorResponseDTO editRegister(Long id, Long register, User actor) {
        Advisor Advisor = repository.findById(id).get();
        logsService.create(actor, Advisor, Collections.singletonList(new ChangeItem("register", (Object) Advisor.getRegister(), (Object) register)), "update");
        Advisor.setRegister(register);
        return repository.save(Advisor).convert();
    }

    /**
     * Edita a senha de um orientador específico.
     *
     * @param id ID da técnica.
     * @param password Nova senha.
     * @param actor Usuário que editou a técnica.
     * @return DTO da resposta contendo a técnica atualizada.
     */
    public AdvisorResponseDTO editPassword(Long id, String password, User actor) {
        Advisor Advisor = repository.findById(id).get();
        logsService.create(actor, Advisor, Collections.singletonList(new ChangeItem("password", (Object) Advisor.getPassword(), (Object) password)), "update");
        Advisor.setPassword(password);
        return repository.save(Advisor).convert();
    }

    /**
     * Edita a senha de um orientador específico.
     * @param Advisor Tecnica a ser editada
     * @param password Nova senha.
     * @return Um booleano indicando se a edição foi bem sucedida
     */
    public boolean editPassword(Advisor Advisor, String password) {

        try {
            Advisor.setPassword(password);
            repository.save(Advisor);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Edita a imagem de um orientador específico.
     * @param id ID da técnica.
     * @param image Nova imagem.
     * @param actor Usuário que editou a técnica.
     * @return DTO da resposta contendo a técnica atualizada.
     */
    public AdvisorResponseDTO editImage(Long id, String image, User actor) {
        Advisor Advisor = repository.findById(id).get();
        logsService.create(actor, Advisor, Collections.singletonList(new ChangeItem("image", (Object) Advisor.getImage(), (Object) image)), "update");
        Advisor.setImage(image);
        return repository.save(Advisor).convert();
    }

    /**
     * Mostra uma lista paginada de técnicas.
     *
     * @param pageable Informações de paginação.
     * @return Página de técnicas na forma de DTOs de resposta.
     * @throws NaoEncontradoException se nenhum orientador  for encontrado.
     */
    public Page<AdvisorResponseDTO> findAdvisors(Pageable pageable) {
        try {
            return repository.findAll(pageable).map(Advisor::convert);
        } catch (Exception e) {
            throw new NaoEncontradoException("Orientadors nao encontrados");
        }
    }



    /**
     * Busca um orientador  pelo seu ID.
     *
     * @param id ID da técnica.
     * @return DTO da resposta contendo a técnica encontrado.
     * @throws NaoEncontradoException se a técnica não for encontrado.
     */
    public AdvisorResponseDTO findAdvisorById(Long id) {
        try {
            return repository.findById(id).get().convert();
        } catch (Exception e) {
            throw new NaoEncontradoException("Orientador nao encontrado");
        }
    }

    /**
     * Busca um orientador  pelo seu email.
     *
     * @param email Email da técnica.
     * @return DTO da resposta contendo a técnica encontrado.
     * @throws NaoEncontradoException se a técnica não for encontrado.
     */
    public AdvisorResponseDTO findAdvisorByEmail(String email) {
        try {
            return repository.findByEmail(email).convert();
        } catch (Exception e) {
            throw new NaoEncontradoException("Orientador nao encontrado");
        }
    }

    /**
     * Busca um {@link Advisor} pelo email
     * @param email o email do tecnico
     * @return {@link Advisor} o tecnico encontrado
     * Utilizado na autenticação
     */
    public Advisor findObjectAdvisor ( String email) {
        try {
            return repository.findByEmail(email);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Busca um {@link Advisor} pelo email
     * @param id o id do tecnico
     * @return {@link Advisor} o tecnico encontrado
     * Utilizado na autenticação
     * @author Gustavo Stinghen
     * @since 28/03/2025
     */
    public Advisor findObjectAdvisor ( Long id) {
        try {
            return repository.findById(id).get();
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * Adiciona uma {@link Notification} a um {@link Advisor}
     * @param id o identificador do tecnico
     * @param notification a notificação a ser adicionada
     * @return {@link AdvisorResponseDTO} o tecnico atualizado
     */
    public AdvisorResponseDTO addNotification(Long id, Notification notification) {
        Advisor Advisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Tecnico não encontrado"));
        if (!Advisor.addNotification(notification)) {
            throw new NaoEncontradoException("Notificação nao encontrado");
        }
        logsService.create( Advisor, Collections.singletonList( new AddItem("notifications", (Object) notification ) ), "add" );
        return repository.save(Advisor).convert();
    }

    /**
     * Remove uma {@link Notification} de um {@link Advisor}
     * @param id o identificador do tecnico
     * @param notification a notificação a ser removida
     * @return {@link StudentResponseDTO} o tecnico atualizado
     */
    public AdvisorResponseDTO removeNotification(Long id, Notification notification) {
        Advisor Advisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Tecnico não encontrado"));
        if (!Advisor.removeNotification(notification)) {
            throw new NaoEncontradoException("Notificação nao encontrado");
        }
        logsService.create(  Advisor, Collections.singletonList( new AddItem("notifications", (Object) notification ) ), "remove" );
        return repository.save(Advisor).convert();
    }

    /**
     * Deleta um orientador  pelo seu ID.
     * @param id ID da técnica a ser deletada.
     * @param actor Usuário que deletou a técnica.
     * @throws NaoEncontradoException se a técnica não for deletada corretamente.
     */
    public void delete(Long id, User actor) {
        try {
            logsService.create(actor, repository.findById(id).get(), "delete");
            repository.deleteById(id);
        } catch (Exception e) {
            throw new NaoEncontradoException("Orientador nao deletado");
        }
    }

    /**
     * Busca um orientador  pelo seu ID.
     *
     * @param id ID do orientador.
     * @return o orientador encontrado.
     * @throws NaoEncontradoException se o orientador não for encontrado.
     */
    public Advisor getObjectAdvisor(Long id) {
        return repository.findById(id).get();
    }

    /**
     * Busca um orientador  pelo seu email.
     *
     * @param email Email do orientador.
     * @return o orientador encontrado.
     * @throws NaoEncontradoException se o orientador nao for encontrado.
     */
    public Advisor getObjectAdvisor(String email) {
        return repository.findByEmail(email);
    }
}
