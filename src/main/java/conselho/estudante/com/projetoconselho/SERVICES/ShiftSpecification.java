package conselho.estudante.com.projetoconselho.SERVICES;

import conselho.estudante.com.projetoconselho.ENTITY.ADMINISTRATION.Course;
import conselho.estudante.com.projetoconselho.ENTITY.ADMINISTRATION.Shift;
import conselho.estudante.com.projetoconselho.ENTITY.USERS.Teacher;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

/**
 * Classe de especificações de consulta para a entidade {@link Shift}.
 *
 * A classe {@link ShiftSpecification} é responsável por construir uma especificação de consulta
 * dinâmica para realizar buscas inteligentes baseadas em texto. A pesquisa é feita em três
 * entidades: {@link Shift}, {@link Teacher}, e {@link Course}.
 *
 * O objetivo da pesquisa inteligente é permitir que o usuário busque por turnos com base no nome do
 * turno, do professor associado ao turno e do curso relacionado ao turno, usando um texto de pesquisa
 * genérico.
 *
 * @author Cauã Justimiano Dutra
 * @since 17/03/2025
 */
public class ShiftSpecification {

    /**
     * Método que constrói uma especificação de consulta para realizar uma busca inteligente
     * nos campos de nome do turno, nome do professor e nome do curso.
     * <p>
     * Esse método é utilizado para criar um filtro baseado no texto passado, que pode ser
     * parcial. A pesquisa é feita utilizando o operador `LIKE` no banco de dados, e a
     * busca será realizada de forma insensível a maiúsculas/minúsculas.
     * </p>
     *
     * @param text Texto utilizado para a busca. Será aplicado um filtro nos nomes do turno,
     *             professor e curso.
     * @return Uma {@link Specification} que pode ser utilizada em consultas JPA para filtrar
     *         os resultados.
     *
     * @see Shift
     * @see Teacher
     * @see Course
     */
    public static Specification<Shift> smartSearch(String text) {

        // Adiciona % no início e no final do texto para permitir busca parcial
        String finalText = "%" + text + "%";

        return (root, query, criteriaBuilder) -> {
            String searchPattern = "%" + finalText.toLowerCase() + "%";

            // Criando as expressões de LIKE para nome do turno, professor e curso
            Predicate shiftName = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchPattern);

            // Join com a tabela de professores
            Join<Shift, Teacher> teacherJoin = root.join("teachers", JoinType.LEFT);
            Predicate teacherName = criteriaBuilder.like(criteriaBuilder.lower(teacherJoin.get("name")), searchPattern);

            // Join com a tabela de cursos
            Join<Shift, Course> courseJoin = root.join("course", JoinType.LEFT);
            Predicate courseName = criteriaBuilder.like(criteriaBuilder.lower(courseJoin.get("name")), searchPattern);

            // Retorna um OR para que qualquer um dos critérios possa ser verdadeiro
            return criteriaBuilder.or(shiftName, teacherName, courseName);
        };
    }
}
