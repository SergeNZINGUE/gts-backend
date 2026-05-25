package com.gts.backgts.dto;

import java.time.LocalDate;

/**
 * Détail de la consommation carburant pour une mission donnée.
 * Calculé via la formule hybride : (carbtDbt − carbtFin) + Σ ravitaillements pendant la mission.
 */
public record ConsommationMissionDto(

        Long    missionId,
        String  codeMission,
        LocalDate dateTravail,

        // Engin
        Long   enginId,
        String codeEngin,
        String modelEngin,

        // Données brutes jauges (en litres)
        Double carbtDbt,
        Double carbtFin,

        // Décomposition de la formule
        Double deltaReservoir,        // carbtDbt − carbtFin
        Double ravitaillementsLitres, // Σ pleins dont horamètre ∈ [compteurDbt, compteurFin]
        Double consommationReelle,    // deltaReservoir + ravitaillementsLitres

        // Indicateurs normalisés
        Double nbHeures,
        Double ratioLH,              // L/h  — null si nbHeures = 0
        Long   distanceKm,           // null si pas de données km
        Double ratioL100Km,          // L/100 km — null si pas de km

        boolean donneesCompletes     // true si carbtDbt et carbtFin sont renseignés
) {}
