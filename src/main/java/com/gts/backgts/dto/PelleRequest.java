package com.gts.backgts.dto;

import com.gts.backgts.enums.TypeEtatEngins;
import com.gts.backgts.enums.TypeTrain;

import java.time.LocalDate;

public record PelleRequest(
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
        LocalDate dateModification,
        Double profondeurFouille,
        TypeTrain typeTrain
) {
}
