package com.gts.backgts.services;

import com.gts.backgts.dto.AuditLogResponse;
import com.gts.backgts.entites.AuditLog;
import com.gts.backgts.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Transactional
    public void save(AuditLog log) {
        auditLogRepository.save(log);
    }

    @Transactional(readOnly = true)
    public Page<AuditLogResponse> search(
            String username,
            String httpMethod,
            Integer httpStatus,
            LocalDateTime debut,
            LocalDateTime fin,
            Pageable pageable
    ) {
        Specification<AuditLog> spec = Specification.where((Specification<AuditLog>) null);

        if (username != null && !username.isBlank()) {
            spec = spec.and((root, q, cb) ->
                    cb.like(cb.lower(root.get("username")), "%" + username.toLowerCase() + "%"));
        }
        if (httpMethod != null && !httpMethod.isBlank()) {
            spec = spec.and((root, q, cb) ->
                    cb.equal(cb.upper(root.get("httpMethod")), httpMethod.toUpperCase()));
        }
        if (httpStatus != null) {
            spec = spec.and((root, q, cb) ->
                    cb.equal(root.get("httpStatus"), httpStatus));
        }
        if (debut != null) {
            spec = spec.and((root, q, cb) ->
                    cb.greaterThanOrEqualTo(root.get("createdAt"), debut));
        }
        if (fin != null) {
            spec = spec.and((root, q, cb) ->
                    cb.lessThanOrEqualTo(root.get("createdAt"), fin));
        }

        return auditLogRepository.findAll(spec, pageable).map(this::toResponse);
    }

    private AuditLogResponse toResponse(AuditLog log) {
        return new AuditLogResponse(
                log.getId(),
                log.getUsername(),
                log.getRoles(),
                log.getHttpMethod(),
                log.getEndpoint(),
                log.getAction(),
                log.getHttpStatus(),
                log.getErrorMessage(),
                log.getIpAddress(),
                log.getDurationMs(),
                log.getCreatedAt()
        );
    }
}
