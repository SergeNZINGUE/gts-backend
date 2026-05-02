package com.gts.backgts.dto;

import java.time.LocalDate;

public record RegistrationRequest(
        String username,
        String password,
        Boolean active,
        String nomUsers,
        String prenomsUsers,
        String emailUsers,
        Boolean cguUsers,
        String tel1Users,
        String roleCode,
        LocalDate dateCreation,
        LocalDate dateModification
) {
}
