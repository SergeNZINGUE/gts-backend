package com.gts.backgts.dto;

import com.gts.backgts.enums.StatutMission;

import java.time.LocalDate;

public record MissionResumeResponse(
        Long id,
        String codeMission,
        LocalDate dateTravail,
        StatutMission statutMission,
        String lieuMission,
        String descriptionMission,
        Double nbHeures
) {
}
