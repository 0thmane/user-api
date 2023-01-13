package com.assessment.userapi.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
public class LoggingAspect implements ApplicationListener<ContextStartedEvent>  {

    private boolean applicationStarted = false;

    public LoggingAspect() {}

    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        this.applicationStarted = true;
    }



    @Around("applicationPackagePointcut() && restControllerPointcut()")
    public Object logApiCalls(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger log = logger(joinPoint);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        log.info("Calling endpoint with url = '{}' and {} method", request.getRequestURI(), request.getMethod());
        return joinPoint.proceed();
    }

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice.
     * @return result.
     * @throws Throwable throws {@link IllegalArgumentException}.
     */
    @Around("applicationPackagePointcut() && springBeanControllerPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger log = logger(joinPoint);
        String methodSignature = getMethodSignature(joinPoint);
        log.info("Enter: {} with argument[s] = {}", methodSignature , Arrays.toString(joinPoint.getArgs()));
        StopWatch stopWatch = new StopWatch(getMethodSignature(joinPoint));
        stopWatch.start(methodSignature);

        try {
            Object result = joinPoint.proceed();
            stopWatch.stop();
            Object loggedResult = result;
            log.info("Exit: {} with result = {}", methodSignature, loggedResult);
            log.info("Execution time {}", stopWatch.shortSummary());
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}()", Arrays.toString(joinPoint.getArgs()), joinPoint.getSignature().getName());
            throw e;
        }
    }

    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice.
     * @param e exception.
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        Logger log = logger(joinPoint);
        if (log.isDebugEnabled() && this.applicationStarted) {
            log
                    .error(
                            "Exception in {} with cause = '{}' and message = '{}'",
                            getMethodSignature(joinPoint),
                            e.getCause() != null ? e.getCause() : "NULL",
                            e.getMessage(),
                            e
                    );
        } else {
            log
                    .error(
                            "Exception in {} with cause = {}",
                            getMethodSignature(joinPoint),
                            e.getCause() != null ? e.getCause() : "NULL"
                    );
        }
    }

    private String getMethodSignature(JoinPoint joinPoint){
        return String.format("%s -> %s",joinPoint.getSignature().getDeclaringType().getSimpleName(), joinPoint.getSignature().getName());
    }

    private Logger logger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut(
            "within(com.assessment.userapi..*)"
    )
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Web REST endpoints.
     */
    @Pointcut(
            "within(@org.springframework.web.bind.annotation.RestController *)"
    )
    public void restControllerPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut(
            "within(@org.springframework.web.bind.annotation.RestController *)" +
                    " || within(@org.springframework.stereotype.Repository *)" +
                    " || within(@org.springframework.stereotype.Service *)"
    )
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches only Web REST endpoints.
     */
    @Pointcut(
            "within(@org.springframework.web.bind.annotation.RestController *)"
    )
    public void springBeanControllerPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }
}
