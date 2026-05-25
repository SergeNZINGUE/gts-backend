package com.gts.backgts.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * Rapport global de consommation carburant de la flotte sur une période.
 */
public record RapportCarburantResponse(

        LocalDate debut,
        LocalDate fin,
        Long      enginIdFiltre,   // null = toute la flotte

        // ── Totaux flotte ────────────────────────────────────────────────────
        /** Σ litres ravitaillés (ConsommationCarburant) — source comptable */
        Double totalLitresConsommes,
        Double coutTotalCarburant,

        /** Ratio L/h moyen pondéré depuis les missions avec données complètes */
        Double ratioHoraireMoyenFlotte,

        // ── Statistiques de couverture ───────────────────────────────────────
        int    nbEnginsActifs,
        int    nbMissionsTotal,
        int    nbMissionsAvecCarburant,
        /** % de missions ayant les deux jauges renseignées */
        Double tauxCouvertureGlobal,

        /** Bilan engin par engin, trié par consommation décroissante */
        List<BilanCarburantEnginDto> bilansParEngin
) {}
