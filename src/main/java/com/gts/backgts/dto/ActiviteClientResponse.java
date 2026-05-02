package com.gts.backgts.dto;

import java.time.LocalDate;

public record ActiviteClientResponse(
        Long id,
        String codeActClt,
        String description,
        LocalDate dateCreation,
        LocalDate dateModification
) {
}
