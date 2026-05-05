package com.gts.backgts.dto;

import java.time.LocalDate;
import java.util.List;

public record UserResponse(
        Long id,
        String username,
        Boolean active,
        String nomUsers,
        String prenomsUsers,
        String emailUsers,
        String tel1Users,
        Boolean cguUsers,
        LocalDate dateCreation,
        LocalDate dateModification,
        List<String> roles
) {
}