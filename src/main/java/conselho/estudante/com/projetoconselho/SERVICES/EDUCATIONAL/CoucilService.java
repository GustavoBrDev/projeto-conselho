package conselho.estudante.com.projetoconselho.SERVICES.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.EDUCATIONAL.CouncilRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL.CouncilResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.Council;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.DadosDuplicadosException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL.CouncilRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CoucilService {

    private CouncilRepository repository;

    public boolean isCouncilFinished(Council council) {
        return council.getCouncilFinished();
    }

    public CouncilResponseDTO create(CouncilRequestDTO councilRequestDTO) {
        Council council = councilRequestDTO.convert();
        if(repository.existsByClasse(council.getClasse()) && !isCouncilFinished(council)) {
            throw new DadosDuplicadosException("Conselho já cadastrada")
        } else {
            return repository.save(council).toDTO();
        }
    }


}
