package conselho.estudante.com.projetoconselho.services.users.technique;

import conselho.estudante.com.projetoconselho.models.dto.request.users.TechniqueRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.users.StudentResponseDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.users.TechniqueResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.administration.Notification;
import conselho.estudante.com.projetoconselho.models.entity.logs.AddItem;
import conselho.estudante.com.projetoconselho.models.entity.logs.ChangeItem;
import conselho.estudante.com.projetoconselho.models.entity.logs.EditableItem;
import conselho.estudante.com.projetoconselho.models.entity.users.Technique;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import conselho.estudante.com.projetoconselho.models.exceptions.DadosDuplicadosException;
import conselho.estudante.com.projetoconselho.models.exceptions.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.repositories.users.TechniqueRepository;
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
 * Serviço para gerenciar operações relacionadas à entidade {@link Technique}.
 *
 * @author Joana Voigt
 * @since 17/03/2025
 *
 * @see Technique
 * @see TechniqueRequestDTO
 * @see TechniqueResponseDTO
 *
 * Atualizado em 20/03/2025
 * Conexão com o UserLogsService para gerar logs
 * @author Gustavo Stinghen
 * @see UserLogsService
 */

@Service
@AllArgsConstructor
public class TechniqueService {

    private TechniqueRepository repository;
    private EmailService emailService;
    private UserLogsService logsService;
    private static final int passwordLength = 8;

    /**
     * Cria uma nova técnica pedagogica.
     *
     * @param techniqueRequestDTO Dados da técnica a serem criados.
     * @param actor Usuário que criou a técnica.
     * @return DTO da resposta contendo a técnica criada.
     * @throws DadosDuplicadosException se o email ou registro já existirem.
     */
    public TechniqueResponseDTO create(TechniqueRequestDTO techniqueRequestDTO, User actor) {
        Technique technique = techniqueRequestDTO.convert();
        if(repository.existsByEmail(technique.getEmail())) {
            throw new DadosDuplicadosException("Email ja cadastrado");
        } else if (repository.existsByRegister(technique.getRegister())) {
            throw new DadosDuplicadosException("Registro ja cadastrado");
        } else {
            technique.setPassword(generateRandomPassword());
            technique.setCreatedAt( new Date());
            emailService.sendWelcomeEmail(technique.getEmail(), technique.getPassword());
            technique = repository.save(technique);
            logsService.create(actor, technique, "create");
            return technique.toDTO();
        }
    }

    /**
     * Método auxiliar para gerar uma senha aleatória com o tamanho especificado.
     * @return uma String com a senha gerada
     * @author Gustavo Stinghen
     * @since 20/03/2025
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
     * Atualiza uma técnica pedagogica existente.
     *
     * @param id ID da técnica a ser atualizada.
     * @param techniqueRequestDTO Novos dados da técnica.
     * @return DTO da resposta contendo a técnica atualizada.
     * @throws DadosDuplicadosException se o email ou registro já existirem ou se a técnica não for encontrada.
     */
    public TechniqueResponseDTO update(Long id, TechniqueRequestDTO techniqueRequestDTO, User actor) {
        Technique technique = techniqueRequestDTO.convert();
        if (repository.existsById(id)) {
            technique.setId(id);
            if (repository.existsByEmail(technique.getEmail())) {

                if ( ! repository.findByEmail(technique.getEmail()).getId().equals(id) ) {
                    throw new DadosDuplicadosException("Email ja cadastrado");
                }

            } else if (repository.existsByRegister(technique.getRegister())) {

                if ( ! repository.findByRegister(technique.getRegister()).getId().equals(id) ) {
                    throw new DadosDuplicadosException("Registro ja cadastrado");
                }
            }

            technique.setCreatedAt( repository.findById(id).get().getCreatedAt() );
            logsService.create( actor, technique,getEditableItems(repository.findById(id).get(), technique), "update" );
            return repository.save(technique).toDTO();
        }
        throw new NaoEncontradoException("Técnico nao encontrado");
    }

    /**
     * Metodo auxiliar para obter os itens editaveis de uma tecnica
     * @param oldTechnique o objeto tecnica antigo
     * @param newTechnique o objeto tecnica novo
     * @return uma lista com os itens editaveis
     */
    public List<EditableItem> getEditableItems( Technique oldTechnique, Technique newTechnique) {

        List<EditableItem> changes = new ArrayList<>();

        if (! oldTechnique.getName().equals(newTechnique.getName())) {
            changes.add(new ChangeItem("name", (Object) oldTechnique.getName(), (Object) newTechnique.getName()));
        }

        if (! oldTechnique.getEmail().equals(newTechnique.getEmail())) {
            changes.add(new ChangeItem("email", (Object) oldTechnique.getEmail(), (Object) newTechnique.getEmail()));
        }

        if (! oldTechnique.getRegister().equals(newTechnique.getRegister())) {
            changes.add(new ChangeItem("register", (Object) oldTechnique.getRegister(), (Object) newTechnique.getRegister()));
        }

        if (! oldTechnique.getPassword().equals(newTechnique.getPassword())) {
            changes.add(new ChangeItem("password", (Object) oldTechnique.getPassword(), (Object) newTechnique.getPassword()));
        }

        if ( ! oldTechnique.getImage().equals( newTechnique.getImage() ) ) {
            changes.add(new ChangeItem("image", (Object) oldTechnique.getImage(), (Object) newTechnique.getImage()));
        }

        return changes;
    }

    /**
     * Edita o nome de uma técnica específica.
     *
     * @param id ID da técnica.
     * @param name Novo nome.
     * @param actor Usuário que editou a técnica.
     * @return DTO da resposta contendo a técnica atualizada.
     */
    public TechniqueResponseDTO editName(Long id, String name, User actor) {
        Technique technique = repository.findById(id).get();
        logsService.create(actor, technique, Collections.singletonList(new ChangeItem("name", (Object) technique.getName(), (Object) name)), "update");
        technique.setName(name);
        return repository.save(technique).toDTO();
    }

    /**
     * Edita o email de uma técnica específica.
     *
     * @param id ID da técnica.
     * @param email Novo email.
     * @param actor Usuário que editou a técnica.
     * @return DTO da resposta contendo a técnica atualizada.
     */
    public TechniqueResponseDTO editEmail(Long id, String email, User actor) {
        Technique technique = repository.findById(id).get();
        logsService.create(actor, technique, Collections.singletonList(new ChangeItem("email", (Object) technique.getEmail(), (Object) email)), "update");
        technique.setEmail(email);
        return repository.save(technique).toDTO();
    }

    /**
     * Edita o número de registro de uma técnica específica.
     *
     * @param id ID da técnica.
     * @param register Novo número de registro.
     * @param actor Usuário que editou a técnica.
     * @return DTO da resposta contendo a técnica atualizada.
     */
    public TechniqueResponseDTO editRegister(Long id, Long register, User actor) {
        Technique technique = repository.findById(id).get();
        logsService.create(actor, technique, Collections.singletonList(new ChangeItem("register", (Object) technique.getRegister(), (Object) register)), "update");
        technique.setRegister(register);
        return repository.save(technique).toDTO();
    }

    /**
     * Edita a senha de uma técnica específica.
     *
     * @param id ID da técnica.
     * @param password Nova senha.
     * @param actor Usuário que editou a técnica.
     * @return DTO da resposta contendo a técnica atualizada.
     */
    public TechniqueResponseDTO editPassword(Long id, String password, User actor) {
        Technique technique = repository.findById(id).get();
        logsService.create(actor, technique, Collections.singletonList(new ChangeItem("password", (Object) technique.getPassword(), (Object) password)), "update");
        technique.setPassword(password);
        return repository.save(technique).toDTO();
    }

    /**
     * Edita a senha de uma técnica específica.
     * @param technique Tecnica a ser editada
     * @param password Nova senha.
     * @return Um booleano indicando se a edição foi bem sucedida
     * @author Gustavo Stinghen
     * @since 19/03/2025
     */
    public boolean editPassword(Technique technique, String password) {

        try {
            technique.setPassword(password);
            repository.save(technique);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Edita a imagem de uma técnica específica.
     * @param id ID da técnica.
     * @param image Nova imagem.
     * @param actor Usuário que editou a técnica.
     * @return DTO da resposta contendo a técnica atualizada.
     */
    public TechniqueResponseDTO editImage(Long id, String image, User actor) {
        Technique technique = repository.findById(id).get();
        logsService.create(actor, technique, Collections.singletonList(new ChangeItem("image", (Object) technique.getImage(), (Object) image)), "update");
        technique.setImage(image);
        return repository.save(technique).toDTO();
    }

    /**
     * Mostra uma lista paginada de técnicas.
     *
     * @param pageable Informações de paginação.
     * @return Página de técnicas na forma de DTOs de resposta.
     * @throws NaoEncontradoException se nenhuma técnica for encontrada.
     */
    public Page<TechniqueResponseDTO> findTechniques(Pageable pageable) {
        try {
            return repository.findAll(pageable).map(Technique::toDTO);
        } catch (Exception e) {
            throw new NaoEncontradoException("Técnicos nao encontrados");
        }
    }



    /**
     * Busca uma técnica pelo seu ID.
     *
     * @param id ID da técnica.
     * @return DTO da resposta contendo a técnica encontrada.
     * @throws NaoEncontradoException se a técnica não for encontrada.
     */
    public TechniqueResponseDTO findTechniqueById(Long id) {
        try {
            return repository.findById(id).get().toDTO();
        } catch (Exception e) {
            throw new NaoEncontradoException("Técnico nao encontrado");
        }
    }

    /**
     * Busca uma técnica pelo seu email.
     *
     * @param email Email da técnica.
     * @return DTO da resposta contendo a técnica encontrada.
     * @throws NaoEncontradoException se a técnica não for encontrada.
     */
    public TechniqueResponseDTO findTechniqueByEmail(String email) {
        try {
            return repository.findByEmail(email).toDTO();
        } catch (Exception e) {
            throw new NaoEncontradoException("Técnico nao encontrado");
        }
    }

    /**
     * Busca um {@link Technique} pelo email
     * @param email o email do tecnico
     * @return {@link Technique} o tecnico encontrado
     * Utilizado na autenticação
     * @author Gustavo Stinghen
     * @since 19/03/2025
     */
    public Technique findObjectTechnique ( String email) {
        try {
            return repository.findByEmail(email);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Busca um {@link Technique} pelo email
     * @param id o id do tecnico
     * @return {@link Technique} o tecnico encontrado
     * Utilizado na autenticação
     * @author Gustavo Stinghen
     * @since 28/03/2025
     */
    public Technique findObjectTechnique ( Long id) {
        try {
            return repository.findById(id).get();
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * Adiciona uma {@link Notification} a um {@link Technique}
     * @param id o identificador do tecnico
     * @param notification a notificação a ser adicionada
     * @return {@link TechniqueResponseDTO} o tecnico atualizado
     */
    public TechniqueResponseDTO addNotification(Long id, Notification notification) {
        Technique technique = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Tecnico não encontrado"));
        if (!technique.addNotification(notification)) {
            throw new NaoEncontradoException("Notificação nao encontrada");
        }
        logsService.create( technique, Collections.singletonList( new AddItem("notifications", (Object) notification ) ), "add" );
        return repository.save(technique).toDTO();
    }

    /**
     * Remove uma {@link Notification} de um {@link Technique}
     * @param id o identificador do tecnico
     * @param notification a notificação a ser removida
     * @return {@link StudentResponseDTO} o tecnico atualizado
     */
    public TechniqueResponseDTO removeNotification(Long id, Notification notification) {
        Technique technique = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Tecnico não encontrado"));
        if (!technique.removeNotification(notification)) {
            throw new NaoEncontradoException("Notificação nao encontrada");
        }
        logsService.create(  technique, Collections.singletonList( new AddItem("notifications", (Object) notification ) ), "remove" );
        return repository.save(technique).toDTO();
    }

    /**
     * Deleta uma técnica pelo seu ID.
     * @param id ID da técnica a ser deletada.
     * @param actor Usuário que deletou a técnica.
     * @throws NaoEncontradoException se a técnica não for deletada corretamente.
     */
    public void delete(Long id, User actor) {
        try {
            logsService.create(actor, repository.findById(id).get(), "delete");
            repository.deleteById(id);
        } catch (Exception e) {
            throw new NaoEncontradoException("Técnico nao deletado");
        }
    }

    /**
     * Filtra técnicas baseadas em um termo de busca.
     *
     * @param termo Termo a ser buscado (pode ser nome, email ou registro).
     * @param pageable Informações de paginação.
     * @return Página de técnicas que correspondem ao termo de busca na forma de DTOs de resposta.
     */
    public Page<TechniqueResponseDTO> TechniqueFilter(String termo, Pageable pageable) {
        return repository.findAll(TechniqueSpecification.techniqueFilter(termo), pageable)
                .map(Technique::toDTO);
    }
}
