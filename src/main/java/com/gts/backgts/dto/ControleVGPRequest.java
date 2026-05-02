package com.gts.backgts.dto;

import com.gts.backgts.entites.StatutVGP;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record ControleVGPRequest(
        @NotNull(message = "L'engin est obligatoire")
        Long enginId,

        @NotNull(message = "La date du dernier contrôle est obligatoire")
        LocalDate dateDernierControle,

        @NotNull(message = "La date de prochaine échéance est obligatoire")
        LocalDate dateProchaineEcheance,

        String organismeControleur,
        String numeroRapport,

        @NotNull(message = "Le résultat est obligatoire")
        StatutVGP resultat,

        String reserveVGP,

        @NotNull(message = "L'état d'alerte est obligatoire")
        Boolean estAlerteActive
) {
}
