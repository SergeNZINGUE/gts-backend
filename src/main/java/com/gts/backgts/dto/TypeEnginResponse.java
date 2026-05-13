package com.gts.backgts.dto;

import com.gts.backgts.entites.FamilleEngin;

public record TypeEnginResponse(

        Long id,
        String code,
        String libelle,
        FamilleEngin famille,
        String description,
        Boolean actif
) {
}
