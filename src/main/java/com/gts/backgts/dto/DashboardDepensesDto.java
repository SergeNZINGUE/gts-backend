package com.gts.backgts.dto;

/**
 * KPI dépenses opérationnelles pour le tableau de bord — année en cours.
 * Deux sources croisées : ravitaillements carburant + fiches de maintenance.
 */
public record DashboardDepensesDto(

        int annee,

        /** Σ (quantiteLitres × prixUnitaire) des pleins de l'année */
        Double depensesCarburant,

        /** Σ (coutMainOeuvre + Σ piecesConsommees) des maintenances de l'année */
        Double depensesMaintenance,

        /** depensesCarburant + depensesMaintenance */
        Double totalDepenses
) {}
