package com.gts.backgts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EmailRequest
 {
        @NotBlank
        @Email
        private String destinataire;
        @NotBlank
        private  String sujet;
        private  String message;
}
