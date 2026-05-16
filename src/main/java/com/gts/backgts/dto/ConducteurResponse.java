package com.gts.backgts.dto;

import com.gts.backgts.enums.StatutConducteur;

import java.time.LocalDate;

public record ConducteurResponse(
        Long id,
        String codeConducteur,
        String nomConducteur,
        String prenomsConducteur,
        String telephone,
        String permisCond,
        String qualifications,
        String cniRef,
        String cniDateEmi,
        String cniDateExp,
        String cniLieuEtab,
        String imgCni,
        String imgConducteur,
        String imgPermis,
        LocalDate dateDebutEmp,
        LocalDate dateFinEmp,
        LocalDate dateNaissance,
        String typEmpl,
        LocalDate dateCreation,
        LocalDate dateModification,
        Integer etatConducteur,
        StatutConducteur statutConducteur,
        Integer coutHoraireConducteur
) {
}
