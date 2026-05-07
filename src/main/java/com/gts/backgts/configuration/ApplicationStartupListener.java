package com.gts.backgts.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Listens for {@link ApplicationReadyEvent} — fired by Spring Boot only after
 * the embedded Tomcat container is fully started and the application context is
 * completely refreshed.  If this log line appears, the HTTP server is bound and
 * ready to accept requests.  If it never appears, the context failed to start
 * (check earlier WARN/ERROR lines) or the process was restarted before reaching
 * this point.
 */
@Component
public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationStartupListener.class);

    private final Environment environment;

    public ApplicationStartupListener(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        String port = environment.getProperty("server.port", "8080");
        String[] activeProfiles = environment.getActiveProfiles();
        String profiles = activeProfiles.length > 0
                ? String.join(", ", activeProfiles)
                : "default";

        logger.info("==========================================================");
        logger.info("Application started successfully on port {}", port);
        logger.info("Active Spring profiles : {}", profiles);
        logger.info("Tomcat is bound and ready to accept HTTP requests");
        logger.info("==========================================================");
    }
}
