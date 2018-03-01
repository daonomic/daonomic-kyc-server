package io.daonomic.kyc;

import io.daonomic.kyc.configuration.ApiConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Kyc {
    private static final Logger logger = LoggerFactory.getLogger(Kyc.class);

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> logger.error(e.getMessage(), e));
        logger.info("Starting server");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApiConfiguration.class);
        context.registerShutdownHook();
        logger.info("Server started");
    }
}
