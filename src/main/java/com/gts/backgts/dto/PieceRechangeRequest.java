package com.gts.backgts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record PieceRechangeRequest(
        @NotBlank(message = "La désignation est obligatoire")
        String designation,

        String referenceConstructeur,

        @NotNull(message = "La quantité en stock est obligatoire")
        @PositiveOrZero(message = "La quantité en stock doit être positive ou nulle")
        Integer quantiteEnStock,

        @NotNull(message = "Le seuil d'alerte stock est obligatoire")
        @PositiveOrZero(message = "Le seuil d'alerte stock doit être positif ou nul")
        Integer seuilAlerteStock,

        @NotNull(message = "Le prix unitaire d'achat est obligatoire")
        @PositiveOrZero(message = "Le prix unitaire d'achat doit être positif ou nul")
        Double prixUnitaireAchat,

        LocalDate dateCreation,
        LocalDate dateModification
) {
}
