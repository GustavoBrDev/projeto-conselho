package conselho.estudante.com.projetoconselho.SERVICES.ADMINISTRATION;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Subject;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

/**
 * Classe que define especificações para filtragem de {@link Subject} com base em critérios dinâmicos.
 *
 * @author Joana Voigt
 * @since 17/03/2025
 *
 * @see Subject
 */
public class SubjectSpecification {
    /**
     * Cria uma especificação para filtrar matérias com base em um termo de busca.
     *
     * @param termo O termo de busca a ser utilizado.
     * @return Uma {@link Specification} que pode ser usada para buscar matérias com base no termo fornecido.
     */
    public static Specification<Subject> subjectFilter(String termo) {
        return (root, query, criteriaBuilder) -> {
            String finalTermo = termo.toLowerCase();

            // Criar predicado para o campo `name`
            Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + finalTermo + "%");

            Predicate workloadPredicate;
            try {
                // Tenta converter o termo em um número inteiro para buscar pelo `workload`
                Integer workload = Integer.parseInt(finalTermo);
                workloadPredicate = criteriaBuilder.equal(root.get("workload"), workload);
            } catch (NumberFormatException e) {
                // Se o termo não for um número, criar um predicado que sempre será falso.
                workloadPredicate = criteriaBuilder.disjunction(); // Predicate sempre falso
            }

            // Combinar todos os predicados usando OR
            return criteriaBuilder.or(namePredicate, workloadPredicate);
        };
    }
}
