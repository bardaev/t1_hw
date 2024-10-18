package org.t1.hw.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.t1.hw.repository.BankRepository;

@Aspect
@Component
public class BankAspect {

    private static final Logger log = LoggerFactory.getLogger(BankAspect.class.getName());

    @Before("@annotation(org.t1.hw.aspect.LogBalance)")
    void getBalanceExecution(JoinPoint joinPoint) {
        log.info("Method " + joinPoint.getSignature().getName() + " was executed");
    }

    @AfterReturning(
            pointcut = "@annotation(org.t1.hw.aspect.LogBalance)",
            returning = "balance"
    )
    void getBalanceReturning(JoinPoint joinPoint, int balance) {
        log.info("Method " + joinPoint.getSignature().getName() + "was returned: " + balance);
    }

    @Around("@annotation(org.t1.hw.aspect.BalanceOperationAspect)")
    Object balanceOperation(ProceedingJoinPoint joinPoint) throws Throwable {
        BankRepository bankRepository = (BankRepository) joinPoint.getTarget();
        log.info("Balance before operation {}: {}", joinPoint.getSignature().getName(), bankRepository.notAspectGetBalance());
        Object retValue = joinPoint.proceed();
        log.info("Balance after operation {}: {}", joinPoint.getSignature().getName(), bankRepository.notAspectGetBalance());
        return retValue;
    }

    @AfterThrowing(
            pointcut = "within(org.t1.hw.repository..*)",
            throwing = "exception"
    )
    void throwException(JoinPoint joinPoint, Exception exception) {
        log.info("Method {}was thrown: {}", joinPoint.getSignature().getName(), exception.getMessage());
    }
}
