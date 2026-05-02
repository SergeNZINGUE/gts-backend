package com.gts.backgts.dto;

import com.gts.backgts.entites.StatutAssurance;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AssuranceEnginRequest(

        Long enginId,
        String numeroPolice,
        String compagnieAssurance,
        LocalDate dateDebut,
        LocalDate dateFin,
        BigDecimal montant,
        String documentUrl,
        StatutAssurance statut,
        LocalDate dateCreation,
        LocalDate dateModification
)
{
}
