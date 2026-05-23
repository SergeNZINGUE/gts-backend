package com.gts.backgts.configuration;

import com.gts.backgts.entites.AuditLog;
import com.gts.backgts.services.AuditLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private static final Logger AUDIT = LoggerFactory.getLogger("AUDIT");

    private final AuditLogService auditLogService;

    @Around("execution(* com.gts.backgts.web.*.*(..))")
    public Object audit(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();

        String username = "anonymous";
        String roles = "";
        String httpMethod = "";
        String endpoint = "";
        String ipAddress = "";

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            username = auth.getName();
            roles = auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(", "));
        }

        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            HttpServletRequest request = attrs.getRequest();
            httpMethod = request.getMethod();
            endpoint = request.getRequestURI();
            ipAddress = resolveClientIp(request);
        }

        MethodSignature sig = (MethodSignature) pjp.getSignature();
        String action = sig.getDeclaringType().getSimpleName() + "#" + sig.getName();

        int status = 200;
        String errorMessage = null;

        try {
            Object result = pjp.proceed();
            return result;
        } catch (Exception ex) {
            status = 500;
            errorMessage = ex.getClass().getSimpleName() + ": " + ex.getMessage();
            throw ex;
        } finally {
            long duration = System.currentTimeMillis() - start;

            AuditLog log = AuditLog.builder()
                    .username(username)
                    .roles(roles)
                    .httpMethod(httpMethod)
                    .endpoint(endpoint)
                    .action(action)
                    .httpStatus(status)
                    .errorMessage(errorMessage)
                    .ipAddress(ipAddress)
                    .durationMs(duration)
                    .createdAt(LocalDateTime.now())
                    .build();

            // Écriture fichier (logger AUDIT)
            AUDIT.info("[{}] {} {} {} | user={} roles={} status={} {}ms{}",
                    log.getCreatedAt(),
                    httpMethod, endpoint, action,
                    username, roles, status, duration,
                    errorMessage != null ? " ERROR=" + errorMessage : "");

            // Écriture base de données
            try {
                auditLogService.save(log);
            } catch (Exception dbEx) {
                AUDIT.error("Échec sauvegarde audit en base : {}", dbEx.getMessage());
            }
        }
    }

    private String resolveClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isBlank()) {
            return ip.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
