package com.gts.backgts.dto;

import java.time.LocalDateTime;

public record AuditLogResponse(
        Long id,
        String username,
        String roles,
        String httpMethod,
        String endpoint,
        String action,
        Integer httpStatus,
        String errorMessage,
        String ipAddress,
        Long durationMs,
        LocalDateTime createdAt
) {}
