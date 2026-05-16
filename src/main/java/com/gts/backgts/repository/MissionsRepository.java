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
}
