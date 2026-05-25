package com.gts.backgts.repository;

import com.gts.backgts.entites.Missions;
import com.gts.backgts.enums.StatutMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MissionsRepository extends JpaRepository<Missions, Long> {

    // Reporting
    List<Missions> findByLocation_Conducteur_IdConducteur(Long conducteurId);
    List<Missions> findByLocation_Conducteur_IdConducteurAndDateDebutMissionBetween(
            Long conducteurId, LocalDate dateDebut, LocalDate dateFin);

    @Query("""
    SELECT m FROM Missions m
    WHERE m.statutMission = :statut
      AND m.dateFinMission <= :date
      AND m.modeCloture IS NULL
""")
    List<Missions> findMissionsACloturer(
            @Param("statut") StatutMission statut,
            @Param("date") LocalDate date
    );

    /** Toutes les missions d'une période (tous engins) */
    @Query("""
        SELECT m FROM Missions m
        JOIN FETCH m.location l
        JOIN FETCH l.engins e
        WHERE m.dateTravail BETWEEN :debut AND :fin
        ORDER BY e.id, m.dateTravail ASC
        """)
    List<Missions> findAllByPeriode(
            @Param("debut") LocalDate debut,
            @Param("fin")   LocalDate fin);

    /** Missions d'un engin spécifique sur une période */
    @Query("""
        SELECT m FROM Missions m
        JOIN FETCH m.location l
        JOIN FETCH l.engins e
        WHERE e.id = :enginId
          AND m.dateTravail BETWEEN :debut AND :fin
        ORDER BY m.dateTravail ASC
        """)
    List<Missions> findByEnginAndPeriode(
            @Param("enginId") Long enginId,
            @Param("debut")   LocalDate debut,
            @Param("fin")     LocalDate fin);
}
