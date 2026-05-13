package com.gts.backgts.dto;

import com.gts.backgts.entites.TypeEngin;
import com.gts.backgts.entites.TypeEtatEngins;

import java.time.LocalDate;

public record EnginRequest(

        String codeEngin,
        String modelEngin,
        LocalDate anneeEngin,
        String immatriculationEngin,
        Long typeEnginId,
        String marqueEngin,
        TypeEtatEngins statusEngin,
        Integer etatEngin,
        String typCarbtEngin,
        LocalDate dateAcqEngin,
        Long coutHorLocEngin,
        Double poidsVide,
        Double horametre,
        LocalDate dateCreation,
        LocalDate dateModification
) {
}
