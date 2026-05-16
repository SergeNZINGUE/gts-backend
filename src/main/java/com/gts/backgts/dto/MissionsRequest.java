package com.gts.backgts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.time.LocalTime;

public record MissionsRequest(
        @NotNull(message = "La date de travail est obligatoire")
        LocalDate dateTravail,


        @PositiveOrZero(message = "Le nombre d'heures doit être positif")
        Double nbHeures,

        LocalTime heureDebutMission,
        LocalTime heureFinMission,


        @PositiveOrZero(message = "Le tarif horaire doit être positif ou nul")
        Double tarifHoraireApplique,

        @NotNull(message = "La location est obligatoire")
        Long locationId,

        @NotNull(message = "Le conducteur est obligatoire")
        Long conducteurId,

        Long factureId,

        String codeMission,
        LocalDate dateDebutMission,
        LocalDate dateFinMission,
        Long kmDbtMission,
        Long kmFinMission,
        Long compteurDbtMission,
        Long compteurFinMission,
        Long carbtDbtMission,
        Long carbtFinMission,
        String materiauxMission,
        Long qteMateriauxMission,


        String statutMission,

        String observationMission,
        String prioriteMission,
        String responsableMission,
        String lieuMission,
        String descriptionMission
) {
}
