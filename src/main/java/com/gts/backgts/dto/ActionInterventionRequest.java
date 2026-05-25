package com.gts.backgts.dto;

import com.gts.backgts.enums.SystemeIntervention;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActionInterventionRequest(

        @NotNull(message = "Le système d'intervention est obligatoire")
        SystemeIntervention systeme,

        @NotBlank(message = "La description de l'action est obligatoire")
        String descriptionAction,

        String resultat
) {}
