package com.gts.backgts.dto;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;

public record ConducteurRequest(

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
         MultipartFile imgCni,
         MultipartFile imgConducteur,
         MultipartFile imgPermis,
         LocalDate dateDebutEmp,
         LocalDate dateFinEmp,
         LocalDate dateNaissance,
         String typEmpl,
         LocalDate dateCreation,
         LocalDate dateModification,
         Integer etatConducteur,
         String statutConducteur

) {
}
