package org.t1.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.t1.logger.HttpLogger;
import org.t1.logger.HttpLoggerAspect;

@Configuration
@ConditionalOnClass(HttpLogger.class)
public class LoggerStarterConfiguration {

    @Bean
    @ConfigurationProperties("t1.http.logger")
    public LoggerProperties loggerProperties() {
        return new LoggerProperties();
    }

    @Bean
    public HttpLogger logger(LoggerProperties loggerProperties) {
        HttpLogger logger = new HttpLogger();
        logger.setLogLevel(loggerProperties.getLogLevel());
        return logger;
    }

    @Bean
    public HttpLoggerAspect httpLoggerAspect(LoggerProperties loggerProperties) {
        HttpLoggerAspect aspect = new HttpLoggerAspect();
        aspect.setLogLevel(loggerProperties.getLogLevel());
        return aspect;
    }
}
