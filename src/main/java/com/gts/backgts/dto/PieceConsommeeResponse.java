package com.gts.backgts.dto;

public record PieceConsommeeResponse(
        Long id,
        Long pieceRechangeId,
        String referencePiece,
        String designation,
        Double quantite,
        String unite,
        Double prixUnitaire,
        Double montantTotal
) {}
