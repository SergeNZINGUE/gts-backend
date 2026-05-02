package com.gts.backgts.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record RapportMissionsConducteurResponse(
        Long conducteurId,
        String conducteurNomComplet,
        int nbMissionsTotal,
        Double totalHeures,
        Long totalKm,
        List<MissionDetail> missions
) {
    public record MissionDetail(
            Long id,
            Long codeMission,
            LocalDate dateDebutMission,
            LocalDate dateFinMission,
            LocalTime heureDebutMission,
            LocalTime heureFinMission,
            String lieuMission,
            Double nbHeures,
            Double tarifHoraireApplique,
            Double sousTotal,
            Long kmDbtMission,
            Long kmFinMission,
            Long carbtDbtMission,
            Long carbtFinMission,
            String materiauxMission,
            Long qteMateriauxMission,
            String statutMission,
            String codeLocation,
            String clientNom
    ) {}
}
