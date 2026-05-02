package com.gts.backgts.dto;

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
        Integer statutConducteur,
        LocalDate dateCreation,
        LocalDate dateModification
) {
}
