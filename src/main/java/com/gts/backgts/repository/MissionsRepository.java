package com.gts.backgts.repository;

import com.gts.backgts.entites.Missions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MissionsRepository extends JpaRepository<Missions, Long> {

    // Reporting
    List<Missions> findByLocation_Conducteur_IdConducteur(Long conducteurId);
    List<Missions> findByLocation_Conducteur_IdConducteurAndDateDebutMissionBetween(
            Long conducteurId, LocalDate dateDebut, LocalDate dateFin);
}
