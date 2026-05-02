package com.gts.backgts.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ConsommationCarburantResponse(

        Long id,
        Long enginId,
        String enginNom,
        Long conducteurId,
        String conducteurNom,
        String conducteurPrenoms,
        LocalDateTime datePlein,
        Double quantiteLitres,
        Double prixUnitaire,
        Double horametreAuPlein,
        String lieuRavitaillement,
        Double coutTotal,
        LocalDate dateCreation,
        LocalDate dateModification
) {
}
