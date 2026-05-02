package com.gts.backgts.dto;

import java.time.LocalDate;
import java.util.Date;

public record LocationsResponse(
        Long id,
        LocalDate dateDebut,
        String statut,
        String codeLocation,
        Integer nbJoursLocation,
        String siteLocation,
        Date dateDbtLoc,
        Date dateFinLoc,
        Long coutHoraireLocation,
        Long coutJournalierLocation,
        Integer nbHeureLocation,
        Integer etatLocation,
        Long clientId,
        String clientDescriptionEntreprise,
        Long conducteurId,
        String conducteurNom,
        String conducteurPrenoms,
        Long enginId,
        String enginCode,
        String enginModel,
        String enginMarque,
        LocalDate dateCreation,
        LocalDate dateModification

) {
}
