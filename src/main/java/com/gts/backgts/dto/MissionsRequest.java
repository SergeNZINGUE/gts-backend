package com.gts.backgts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public record MissionsRequest(
        @NotNull(message = "La date de travail est obligatoire")
        LocalDate dateTravail,

        @NotNull(message = "Le nombre d'heures est obligatoire")
        @PositiveOrZero(message = "Le nombre d'heures doit être positif")
        Double nbHeures,

        LocalTime heureDebutMission,
        LocalTime heureFinMission,

        @NotNull(message = "Le tarif horaire est obligatoire")
        @PositiveOrZero(message = "Le tarif horaire doit être positif ou nul")
        Double tarifHoraireApplique,

        @NotNull(message = "La location est obligatoire")
        Long locationId,

        Long factureId,

        Long codeMission,
        LocalDate dateDebutMission,
        LocalDate dateFinMission,
        Long kmDbtMission,
        Long kmFinMission,
        Long carbtDbtMission,
        Long carbtFinMission,
        String materiauxMission,
        Long qteMateriauxMission,

        @NotBlank(message = "Le statut de la mission est obligatoire")
        String statutMission,

        String observationMission,
        String prioriteMission,
        String responsableMission,
        String lieuMission,
        String descriptionMission
) {
}
