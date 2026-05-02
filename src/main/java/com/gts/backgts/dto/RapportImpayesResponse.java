package com.gts.backgts.dto;

import java.time.LocalDate;
import java.util.List;

public record RapportImpayesResponse(
        int nbFacturesImpayees,
        Double totalMontantHT,
        Double totalMontantTTC,
        Double totalDejaVerse,
        Double totalResteARegler,
        List<FactureImpayeeDetail> factures
) {
    public record FactureImpayeeDetail(
            Long factureId,
            LocalDate dateEmission,
            String etatPaiement,
            Long clientId,
            String clientNom,
            String codeLocation,
            String siteLocation,
            Double montantHT,
            Double montantTTC,
            Double montantDejaVerse,
            Double resteARegler,
            long joursDepuisEmission
    ) {}
}
