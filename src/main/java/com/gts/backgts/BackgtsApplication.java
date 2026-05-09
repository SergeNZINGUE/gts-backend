package com.gts.backgts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(excludeName = {
        "org.springdoc.core.configuration.SpringDocHateoasConfiguration",
        "org.springdoc.core.configuration.SpringDocDataRestConfiguration"
})
public class BackgtsApplication {

    private static final Logger logger = LoggerFactory.getLogger(BackgtsApplication.class);

    public static void main(String[] args) {
        logger.info("BackgtsApplication bootstrap starting...");
        SpringApplication.run(BackgtsApplication.class, args);
        // NOTE: reaching this line means SpringApplication.run() returned normally.
        // The ApplicationReadyEvent listener (ApplicationStartupListener) will log
        // port and profile details once Tomcat is fully bound.
    }

}
