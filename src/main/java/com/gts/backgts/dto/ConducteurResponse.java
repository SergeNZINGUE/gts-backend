package com.gts.backgts.dto;

import com.gts.backgts.entites.StatutConducteur;

import java.time.LocalDate;
import java.util.Date;

public record ConducteurResponse(
        Long id,
        String codeConducteur,
        String nomConducteur,
        String prenomsConducteur,
        String telephone,
        String permisCond,
        String qualifications,
        String cnibRef,
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
        StatutConducteur statutConducteur
) {
}
