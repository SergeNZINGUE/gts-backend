package com.gts.backgts.repository;

import com.gts.backgts.entites.ConsommationCarburant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsommationCarburantRepository extends JpaRepository<ConsommationCarburant, Long> {

    /** Tous les pleins d'un engin, triés par horamètre croissant */
    List<ConsommationCarburant> findByEnginIdOrderByHorametreAuPleinAsc(Long enginId);

    /** Pleins d'un engin sur une période calendaire */
    @Query("""
        SELECT c FROM ConsommationCarburant c
        WHERE c.engin.id = :enginId
          AND c.datePlein >= :debut
          AND c.datePlein <  :fin
        ORDER BY c.horametreAuPlein ASC
        """)
    List<ConsommationCarburant> findByEnginAndPeriode(
            @Param("enginId") Long enginId,
            @Param("debut")   LocalDateTime debut,
            @Param("fin")     LocalDateTime fin);

    /** Tous les pleins de la flotte sur une période */
    @Query("""
        SELECT c FROM ConsommationCarburant c
        WHERE c.datePlein >= :debut
          AND c.datePlein <  :fin
        ORDER BY c.engin.id, c.horametreAuPlein ASC
        """)
    List<ConsommationCarburant> findAllByPeriode(
            @Param("debut") LocalDateTime debut,
            @Param("fin")   LocalDateTime fin);

    /** Pleins d'un engin dans un intervalle horamètre — pour lier à une mission */
    @Query("""
        SELECT c FROM ConsommationCarburant c
        WHERE c.engin.id = :enginId
          AND c.horametreAuPlein >= :hMin
          AND c.horametreAuPlein <= :hMax
        """)
    List<ConsommationCarburant> findByEnginAndHorametreBetween(
            @Param("enginId") Long enginId,
            @Param("hMin")    double hMin,
            @Param("hMax")    double hMax);

    /**
     * Coût total des ravitaillements sur une période (agrégat pur, pas d'entité chargée).
     * Retourne 0.0 si aucun plein sur la période.
     */
    @Query("""
        SELECT COALESCE(SUM(c.quantiteLitres * c.prixUnitaire), 0.0)
        FROM ConsommationCarburant c
        WHERE c.datePlein >= :debut
          AND c.datePlein <  :fin
        """)
    Double sumCoutCarburant(
            @Param("debut") LocalDateTime debut,
            @Param("fin")   LocalDateTime fin);
}
