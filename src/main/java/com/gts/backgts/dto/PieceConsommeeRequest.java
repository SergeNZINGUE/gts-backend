package com.gts.backgts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PieceConsommeeRequest(

        /** FK vers le catalogue pièces (nullable — saisie libre autorisée) */
        Long pieceRechangeId,

        /** Référence constructeur (libre ou copiée depuis le catalogue) */
        String referencePiece,

        @NotBlank(message = "La désignation est obligatoire")
        String designation,

        @NotNull(message = "La quantité est obligatoire")
        @Positive(message = "La quantité doit être positive")
        Double quantite,

        @NotBlank(message = "L'unité est obligatoire")
        String unite,

        Double prixUnitaire
) {}
