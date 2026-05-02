package com.gts.backgts.dto;

import com.gts.backgts.entites.TypeEtatEngins;

import java.time.LocalDate;

public record CamionResponse(

        Long id,
        String codeEngin,
        String modelEngin,
        LocalDate anneeEngin,
        String immatriculationEngin,
        String marqueEngin,
        Integer etatEngin,
        TypeEtatEngins statusEngin,
        String typCarbtEngin,
        LocalDate dateAcqEngin,
        Long coutHorLocEngin,
        Double poidsVide,
        Double horametre,
        Double capaciteCharge,
        Integer nombreEssieux,
        Double volumeBenne
) {
}
