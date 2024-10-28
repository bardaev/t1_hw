package org.t1.hw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
@EnableScheduling
public class OutboxSchedulerConfig {

    @Bean(name = "registerExecutor")
    public Executor taskExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Bean(name = "outboxPersistExecutor")
    public Executor persistExecutor() {
        return Executors.newSingleThreadExecutor();
    }
}
