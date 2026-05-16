package com.gts.backgts.dto;

import java.time.LocalDate;
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
        String statutMission,
        String observationMission,
        String prioriteMission,
        String responsableMission,
        String lieuMission,
        String descriptionMission,
        LocalDate dateCreation,
        LocalDate dateModification,
        Long conducteurId,
        String codeConducteur,
        String nomConducteur,
        String prenomsConducteur,
        String telephone
) {
}
