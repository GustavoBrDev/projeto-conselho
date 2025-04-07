package conselho.estudante.com.projetoconselho.repositories.educational;

import conselho.estudante.com.projetoconselho.models.entity.educational.RepresentativePreCouncil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repositório para operações de persistência relacionadas a pré-conselhos de representantes.
 *
 * <p>Esta interface estende {@link JpaRepository} e {@link JpaSpecificationExecutor} para fornecer
 * operações CRUD básicas e consultas específicas para a entidade {@link RepresentativePreCouncil}.</p>
 *
 * @author Alex Zastrow
 * @since 24/03/2025
 *
 * @see RepresentativePreCouncil
 * @see JpaRepository
 * @see JpaSpecificationExecutor
 */
@Repository
public interface RepresentativePreCouncilRepository extends
        JpaRepository<RepresentativePreCouncil, Long>,
        JpaSpecificationExecutor<RepresentativePreCouncil> {


    /**
     * Busca pré-conselhos por ID da classe associada.
     *
     * @param classeId ID da classe para filtro
     * @param pageable Configuração de paginação
     * @return Página de pré-conselhos da classe especificada
     */
    /*@Query("SELECT p FROM RepresentativePreCouncil p WHERE p.classe.id = :classeId")
    Page<RepresentativePreCouncil> findByClasse(@Param("classeId") Long classeId, Pageable pageable);*/


    /**
     * Realiza uma busca inteligente em pré-conselhos por múltiplos critérios.
     *
     * A busca é realizada nos seguintes campos:
     *   Nome da classe associada
     *   ID do conselho (convertido para string)
     *   Nome dos professores associados
     *
     * @param term Termo de busca (case-insensitive)
     * @param pageable Configuração de paginação
     * @return Página de pré-conselhos que correspondem ao critério de busca
     */
    /*@Query("SELECT DISTINCT p FROM RepresentativePreCouncil p " +
            "LEFT JOIN p.classe c " +
            "LEFT JOIN p.council co " +
            "LEFT JOIN p.teachers t " +
            "WHERE (LOWER(c.name) LIKE LOWER(CONCAT('%', :term, '%')) " +
            "OR CAST(co.id AS string) LIKE CONCAT('%', :term, '%') " +
            "OR LOWER(t.name) LIKE LOWER(CONCAT('%', :term, '%'))")
    Page<RepresentativePreCouncil> search(@Param("term") String term, Pageable pageable);*/


    /**
     * Filtra pré-conselhos por intervalo de datas.
     *
     * Considera três cenários:
     *   Pré-conselhos que começam dentro do intervalo
     *   Pré-conselhos que terminam dentro do intervalo
     *   Pré-conselhos que abrangem todo o intervalo
     *
     * @param startDate Data de início do intervalo
     * @param endDate Data de fim do intervalo
     * @param pageable Configuração de paginação
     * @return Página de pré-conselhos no intervalo especificado
     */
    /*@Query("SELECT p FROM RepresentativePreCouncil p WHERE " +
            "(p.startDate BETWEEN :startDate AND :endDate) OR " +
            "(p.endDate BETWEEN :startDate AND :endDate) OR " +
            "(p.startDate <= :startDate AND p.endDate >= :endDate)")
    Page<RepresentativePreCouncil> findByDateRange(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            Pageable pageable);*/


    /**
     * Filtra pré-conselhos por status de preenchimento.
     *
     * @param isFilled Status de preenchimento para filtro (true/false)
     * @param pageable Configuração de paginação
     * @return Página de pré-conselhos com o status especificado
     */
    @Query("SELECT p FROM RepresentativePreCouncil p WHERE p.isFilled = :isFilled")
    Page<RepresentativePreCouncil> findByFillStatus(
            @Param("isFilled") Boolean isFilled,
            Pageable pageable);


    /**
     * Filtra pré-conselhos por classe e status de preenchimento.
     *
     * @param classeId ID da classe para filtro
     * @param isFilled Status de preenchimento para filtro (true/false)
     * @param pageable Configuração de paginação
     * @return Página de pré-conselhos que correspondem aos critérios
     */
    @Query("SELECT p FROM RepresentativePreCouncil p WHERE " +
            "p.classe.id = :classeId AND p.isFilled = :isFilled")
    Page<RepresentativePreCouncil> findByClasseAndFillStatus(
            @Param("classeId") Long classeId,
            @Param("isFilled") Boolean isFilled,
            Pageable pageable);


    /**
     * Verifica se existem pré-conselhos associados a uma classe.
     *
     * @param classeId ID da classe para verificação
     * @return true se existir pelo menos um pré-conselho associado, false caso contrário
     */
    boolean existsByClasseId(Long classeId);
}
