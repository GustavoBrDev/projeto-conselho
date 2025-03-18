package SERVICES.USERS;

import MODELS.ENTITY.USERS.Technique;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

/**
 * Classe de especificação que define critérios de busca dinâmicos para a entidade {@link Technique}.
 * Permite filtrar técnicas por nome, email ou número de registro usando um único termo de busca.
 *
 * @author Joana Voigt
 * @since 17/03/2025
 *
 * @see Technique
 */
public class TechniqueSpecification {
    /**
     * Cria uma especificação para filtrar técnicas com base em um termo.
     *
     * @param termo Termo de busca que pode representar parte de um nome, email ou o número de registro.
     * @return {@link Specification} para ser usada em consultas dinâmicas.
     */
    public static Specification<Technique> techniqueFilter(String termo) {
        return (root, query, criteriaBuilder) -> {
            String finalTermo = termo.toLowerCase();

            // Criando predicados para cada um dos campos
            Predicate nomePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + finalTermo + "%");
            Predicate emailPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + finalTermo + "%");

            Predicate registroPredicate;
            try {
                Long registro = Long.parseLong(finalTermo);
                registroPredicate = criteriaBuilder.equal(root.get("register"), registro);
            } catch (NumberFormatException e) {
                // Se o termo não for um número, garanti que o predicate sempre retorna falso.
                registroPredicate = criteriaBuilder.disjunction(); // Predicate sempre falso
            }

            // Combinar todos os predicados usando OR
            return criteriaBuilder.or(nomePredicate, emailPredicate, registroPredicate);
        };
    }
}

