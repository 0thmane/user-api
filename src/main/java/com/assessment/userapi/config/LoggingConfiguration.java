package com.assessment.userapi.config;

import com.assessment.userapi.aop.LoggingAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.annotation.PostConstruct;

@Configuration
@EnableAspectJAutoProxy
public class LoggingConfiguration {

    private final Logger log = LoggerFactory.getLogger(LoggingConfiguration.class);

    @PostConstruct
    public void postContruct() {
        log.info("Configuring logging");
    }

    @Bean
    public LoggingAspect loggingAspect() {
        log.info("Adding logging aspect bean");
        return new LoggingAspect();
    }
}
