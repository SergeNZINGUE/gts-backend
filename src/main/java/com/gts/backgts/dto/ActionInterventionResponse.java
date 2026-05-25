package com.gts.backgts.dto;

import com.gts.backgts.enums.SystemeIntervention;

public record ActionInterventionResponse(
        Long id,
        SystemeIntervention systeme,
        String descriptionAction,
        String resultat
) {}
