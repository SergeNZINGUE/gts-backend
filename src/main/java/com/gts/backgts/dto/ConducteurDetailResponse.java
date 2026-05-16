package com.gts.backgts.dto;

import com.gts.backgts.enums.StatutConducteur;

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
        Integer etatConducteur,
        StatutConducteur statutConducteur,
        Long nombreMissions,
        LocalDate dateDebutEmp,
        LocalDate dateFinEmp,
        LocalDate dateNaissance,
        String typEmpl,
        String imgConducteur,
        Integer page,
        Integer size,
        Integer totalPages,
        List<MissionResumeResponse> missions,
        Integer coutHoraireConducteur
) {
}
