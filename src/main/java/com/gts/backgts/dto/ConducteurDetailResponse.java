package com.gts.backgts.dto;

import java.time.LocalDate;
import java.util.List;

public record ConducteurDetailResponse(
        Long conducteurId,
        String codeConducteur,
        String nomConducteur,
        String prenomsConducteur,
        String telephone,
        String permisCond,
        String qualifications,
        Integer statutConducteur,
        Long nombreMissions,
        LocalDate dateDebutEmp,
        LocalDate dateFinEmp,
        LocalDate dateNaissance,
        String typEmpl,
        String imgConducteur,
        Integer page,
        Integer size,
        Integer totalPages,
        List<MissionResumeResponse> missions
) {
}
