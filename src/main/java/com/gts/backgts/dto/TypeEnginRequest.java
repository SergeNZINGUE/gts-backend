package com.gts.backgts.dto;

import com.gts.backgts.enums.FamilleEngin;

public record TypeEnginRequest(
        String code,
        String libelle,
        FamilleEngin famille,
        String description,
        Boolean actif

) {
}
