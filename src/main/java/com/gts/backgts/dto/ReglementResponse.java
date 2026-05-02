package com.gts.backgts.dto;

import java.time.LocalDate;

public record ReglementResponse(

        Long id,
        Long factureId,
        String factureEtatPaiement,
        Long clientId,
        String clientDescriptionEntreprise,
        LocalDate dateReglement,
        Double montantVerse,
        String modePaiement,
        Double montantTotalFacture,
        Double montantDejaPaye,
        Double resteAPayer,
        LocalDate dateCreation,
        LocalDate dateModification
) {
}
