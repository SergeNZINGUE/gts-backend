package com.gts.backgts.dto;

import java.util.Date;
import java.util.List;

public record RapportLocationClientResponse(
        Long clientId,
        String clientNom,
        int nbLocationsTotal,
        int nbLocationsEnCours,
        int nbLocationsTerminees,
        Double totalMontantMissionsHT,
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
            String enginModel,
            String enginMarque,
            String conducteurNomComplet,
            Integer nbJoursLocation,
            Integer nbHeureLocation,
            Long coutHoraireLocation,
            Long coutJournalierLocation,
            Double montantMissionsHT
    ) {}
}
