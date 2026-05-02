package com.gts.backgts.dto;

import java.time.LocalDate;

public record PieceRechangeResponse(
        Long id,
        String designation,
        String referenceConstructeur,
        Integer quantiteEnStock,
        Integer seuilAlerteStock,
        Double prixUnitaireAchat,
        Boolean alerteReapprovisionnement,
        String messageAlerteStock,
        LocalDate dateCreation,
        LocalDate dateModification

) {
}
