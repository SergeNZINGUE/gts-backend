package com.gts.backgts.dto;

import java.time.LocalDateTime;

public record ConsommationCarburantRequest(

        Long enginId,
        Long conducteurId,
        LocalDateTime datePlein,
        Double quantiteLitres,
        Double prixUnitaire,
        Double horametreAuPlein,
        String lieuRavitaillement
) {
}
