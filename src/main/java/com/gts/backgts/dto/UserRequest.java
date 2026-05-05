package com.gts.backgts.dto;

public record UserRequest(
        String username,
        String nomUsers,
        String prenomsUsers,
        String emailUsers,
        String tel1Users,
        Boolean active,
        Boolean cguUsers,
        String roleCode
) {
}