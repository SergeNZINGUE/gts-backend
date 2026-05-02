package com.gts.backgts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.util.List;

public record FactureRequest(
        @NotNull(message = "La location est obligatoire")
        Long locationId,

        @NotNull(message = "La date d'émission est obligatoire")
        LocalDate dateEmission,

        @PositiveOrZero(message = "La TVA doit être positive ou nulle")
        Double tauxTVA,

        @NotBlank(message = "L'état de paiement est obligatoire")
        String etatPaiement,

        List<Long> missionIds
) {
}
