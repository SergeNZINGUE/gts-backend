package com.gts.backgts.dto;

import com.gts.backgts.enums.ModeCloture;
import com.gts.backgts.enums.StatutMission;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record MissionsResponse(

        Long id,
        LocalDate dateTravail,
        Double nbHeures,
        LocalTime heureDebutMission,
        LocalTime heureFinMission,
        Double tarifHoraireApplique,
        Double sousTotal,
        Long locationId,
        String codeLocation,
        Long factureId,
        String factureEtatPaiement,
        String codeMission,
        LocalDate dateDebutMission,
        LocalDate dateFinMission,
        Long kmDbtMission,
        Long kmFinMission,
        Long compteurDbtMission,
        Long compteurFinMission,
        Long carbtDbtMission,
        Long carbtFinMission,
        String materiauxMission,
        Long qteMateriauxMission,
        StatutMission statutMission,
        String observationMission,
        String prioriteMission,
        String responsableMission,
        String lieuMission,
        String descriptionMission,
        LocalDate dateCreation,
        LocalDate dateModification,
        ModeCloture modeCloture,      // nouveau
        LocalDateTime dateCloture,    // nouveau
        Long conducteurId,
        String codeConducteur,
        String nomConducteur,
        String prenomsConducteur,
        String telephone
) {
}
