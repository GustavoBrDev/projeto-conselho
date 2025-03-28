package conselho.estudante.com.projetoconselho.SERVICES.EDUCATIONAL.COUNCIL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.Council;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Classe de serviços agendados para finalizar os conselhos pendentes
 * @author Gustavo Stinghen
 * @since 27/03/2025
 * @see CouncilService
 */

@Component
@AllArgsConstructor
public class CouncilScheduler {

    private CouncilService service;

    /**
     * Método que finaliza os conselhos pendentes que já passaram da data limite
     * Verifica a cada dia as 00:00
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void schedulePreCouncilFinalization() {
        List<Council> pendingCouncils = service.getActiveCouncils(new Date());
        for (Council council : pendingCouncils) {
            try {

                if (council.getTeacherPreCouncilEndDate().before(new Date())) {
                    service.endTeacherPreCouncil(council.getId());
                } else if (council.getRepresentativePreCouncilEndDate().before(new Date())) {
                    service.endRepresentativePreCouncil(council.getId());
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
