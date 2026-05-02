package com.gts.backgts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record ReglementRequest(
        @NotNull(message = "La facture est obligatoire")
        Long factureId,

        @NotNull(message = "Le client est obligatoire")
        Long clientId,

        @NotNull(message = "La date de règlement est obligatoire")
        LocalDate dateReglement,

        @NotNull(message = "Le montant versé est obligatoire")
        @Positive(message = "Le montant versé doit être strictement positif")
        Double montantVerse,

        @NotBlank(message = "Le mode de paiement est obligatoire")
        String modePaiement
) {
}
