package com.gts.backgts.dto;

import com.gts.backgts.enums.TypeMouvement;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record EtatDesLieuxResponse(

        Long id,
        Long locationId,
        String locationCode,
        LocalDateTime dateInventaire,
        TypeMouvement type,
        Double niveauCarburant,
        Double horametreConstat,
        String observationsGenerales,
        Boolean aDesDegats,
        String photosLien,
        Long inspecteurId,
        String inspecteurNom,
        String inspecteurPrenoms,
        LocalDate dateCreation,
        LocalDate dateModification
) {
}
