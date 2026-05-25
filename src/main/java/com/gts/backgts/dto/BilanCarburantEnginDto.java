package com.gts.backgts.dto;

import java.util.List;

/**
 * Bilan carburant complet d'un engin sur une période.
 * Deux sources croisées : ravitaillements (pleins) + jauges missions.
 */
public record BilanCarburantEnginDto(

        Long   enginId,
        String codeEngin,
        String modelEngin,

        // ── Source 1 : Ravitaillements (ConsommationCarburant) ─────────────
        /** Σ litres versés sur la période */
        Double totalLitresPleins,
        /** Σ coût des pleins (litres × prix unitaire) */
        Double coutTotalPleins,
        /** Δ horamètre entre premier et dernier plein */
        Double deltaHorametre,
        /** Ratio global L/h calculé sur les pleins (source la plus fiable) */
        Double ratioHorairePleins,

        // ── Source 2 : Jauges missions ──────────────────────────────────────
        int    nbMissionsTotal,
        int    nbMissionsAvecCarburant,
        /** % de missions avec données carburant renseignées */
        Double tauxCouverture,
        /** Σ consommationReelle des missions avec données complètes */
        Double consommationMissions,
        /** Ratio L/h moyen pondéré, calculé depuis les missions */
        Double ratioHoraireMissions,

        /** Détail mission par mission */
        List<ConsommationMissionDto> detailMissions
) {}
