package com.gts.backgts.dto;

import com.gts.backgts.enums.StatutVGP;

import java.time.LocalDate;

public record ControleVGPResponse(
        Long id,
        Long enginId,
        String enginCode,
        String enginModel,
        LocalDate dateDernierControle,
        LocalDate dateProchaineEcheance,
        String organismeControleur,
        String numeroRapport,
        StatutVGP resultat,
        String reserveVGP,
        Boolean estAlerteActive,
        LocalDate dateCreation,
        LocalDate dateModification
) {
}
