package com.gts.backgts.dto;

import java.time.LocalDate;

public record RoleRequest(

        String codeTypeRole,
        String rolename,
        LocalDate dateCreation,
        LocalDate dateModification
) {
}
