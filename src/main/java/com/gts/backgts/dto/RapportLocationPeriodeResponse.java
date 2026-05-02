package com.gts.backgts.dto;

import java.util.Date;
import java.util.List;

public record RapportLocationPeriodeResponse(
        int nbLocationsTotal,
        int nbLocationsEnCours,
        int nbLocationsTerminees,
        List<LocationDetail> locations
) {
    public record LocationDetail(
            Long id,
            String codeLocation,
            Date dateDbtLoc,
            Date dateFinLoc,
            String statut,
            Integer etatLocation,
            String siteLocation,
            Long clientId,
            String clientNom,
            Long conducteurId,
            String conducteurNomComplet,
            Long enginId,
            String enginModel,
            String enginMarque,
            Integer nbJoursLocation,
            Integer nbHeureLocation,
            Long coutHoraireLocation,
            Long coutJournalierLocation,
            Double montantMissionsHT
    ) {}
}
