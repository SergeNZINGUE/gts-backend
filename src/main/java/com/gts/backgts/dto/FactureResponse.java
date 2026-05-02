package com.gts.backgts.dto;

import java.time.LocalDate;
import java.util.List;

public record FactureResponse(
        Long id,
        LocalDate dateEmission,
        Double tauxTVA,
        String etatPaiement,
        LocalDate dateCreation,
        LocalDate dateModification,

        // Location
        Long locationId,
        String codeLocation,
        String siteLocation,          // manquant

        // Client (résolu via location.client)
        Long clientId,                // manquant
        String clientNom,             // manquant

        // Calculs
        Double montantHT,
        Double montantTTC,

        // Relations
        List<MissionFactureeDTO> missionsFacturees,   // manquant
        List<ReglementDTO> reglements                 // manquant
) {

    public record MissionFactureeDTO(
            Long id,
            Long codeMission,
            String lieuMission,
            Double nbHeures,
            Double tarifHoraireApplique,
            Double sousTotal,
            String statutMission
    ) {}

    public record ReglementDTO(
            Long id,
            LocalDate dateReglement,
            Double montantVerse,
            String modePaiement
    ) {}
}