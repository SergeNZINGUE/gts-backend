package com.gts.backgts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailResponse
{
    private Boolean succes;
    private String message;
    private LocalDateTime dateEnvoi;
}
