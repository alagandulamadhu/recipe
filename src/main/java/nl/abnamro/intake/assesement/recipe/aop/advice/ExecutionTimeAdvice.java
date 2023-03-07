package nl.abnamro.intake.assesement.recipe.aop.advice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class ExecutionTimeAdvice {

    private static final Logger LOGGER = LogManager.getLogger(ExecutionTimeAdvice.class);

    @Around("@annotation(nl.abnamro.intake.assesement.recipe.aop.annotation.ExecutionTime)")
    public Object executionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            return joinPoint.proceed();
        } finally {
            stopWatch.stop();
            LOGGER.warn("Time taken by " + joinPoint.getSignature().getName() + "() method in class " + joinPoint.getSignature().getDeclaringType() + " is : " + stopWatch.getTotalTimeMillis() + " ms");
        }
    }
}
