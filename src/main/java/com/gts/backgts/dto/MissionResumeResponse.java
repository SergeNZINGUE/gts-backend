package com.gts.backgts.dto;

import java.time.LocalDate;

public record MissionResumeResponse(
        Long id,
        String codeMission,
        LocalDate dateTravail,
        String statutMission,
        String lieuMission,
        String descriptionMission,
        Double nbHeures
) {
}
