package conselho.estudante.com.projetoconselho.SERVICES.EDUCATIONAL;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.TeacherPreCouncil;

import org.springframework.data.jpa.domain.Specification;

public class TeacherPreCouncilSpecification {

    // Filtro por pesquisa inteligente (nome do professor, matéria, curso)
    public static Specification<TeacherPreCouncil> filtroContendoTexto(String texto) {
        if (texto == null || texto.isEmpty()) {
            return null;  // Se o texto for vazio ou nulo, ignora o filtro de texto
        }
        String finalTexto = "%" + texto.toLowerCase() + "%";

        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("teacher").get("name")), finalTexto), // Filtro por nome do professor
                criteriaBuilder.like(criteriaBuilder.lower(root.get("subject").get("name")), finalTexto), // Filtro por nome da matéria
                criteriaBuilder.like(criteriaBuilder.lower(root.get("course").get("name")), finalTexto)  // Filtro por nome do curso
        );
    }

    // Filtro por nome do professor
    public static Specification<TeacherPreCouncil> filtroPorProfessor(String professor) {
        if (professor == null || professor.isEmpty()) {
            return null; // Se o professor for vazio ou nulo, ignora o filtro de professor
        }
        String finalProfessor = "%" + professor.toLowerCase() + "%";

        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("teacher").get("name")), finalProfessor);
    }

    // Filtro por nome da matéria
    public static Specification<TeacherPreCouncil> filtroPorMateria(String materia) {
        if (materia == null || materia.isEmpty()) {
            return null; // Se a matéria for vazia ou nula, ignora o filtro de matéria
        }
        String finalMateria = "%" + materia.toLowerCase() + "%";

        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("subject").get("name")), finalMateria);
    }

    // Filtro por nome do curso
    public static Specification<TeacherPreCouncil> filtroPorCurso(String curso) {
        if (curso == null || curso.isEmpty()) {
            return null; // Se o curso for vazio ou nulo, ignora o filtro de curso
        }
        String finalCurso = "%" + curso.toLowerCase() + "%";

        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("course").get("name")), finalCurso);
    }
}
