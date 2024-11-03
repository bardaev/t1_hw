package org.t1.logger;

import ch.qos.logback.classic.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;

@Aspect
public class HttpLoggerAspect {

    private final Logger log = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(HttpLoggerAspect.class);

    public void setLogLevel(ch.qos.logback.classic.Level level) {
        log.setLevel(level);
    }

    @Around("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public Object logGetRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Execution controller {}", joinPoint.getSignature().getName());
        return joinPoint.proceed();
    }
}
