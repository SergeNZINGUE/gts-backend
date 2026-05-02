package com.gts.backgts.dto;

import java.time.LocalDate;

public record RoleResponse(

        Long id,
        String codeTypeRole,
        String rolename,
        LocalDate dateCreation,
        LocalDate dateModification
) {
}
