package com.qaq.base.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Order(1)
public class LogAspect {

    @Around("within(@org.springframework.web.bind.annotation.RestController *) || within(@org.springframework.stereotype.Controller *)")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        String queryString = null;
        String requestMethod = null;
        String requestUri = null;

        if (attributes != null && attributes.getRequest() != null) {
            var request = attributes.getRequest();
            queryString = request.getQueryString();
            requestMethod = request.getMethod();
            requestUri = request.getRequestURI();
        }

        try {
            Object result = joinPoint.proceed();
            var responseStatus = attributes != null && attributes.getResponse() != null ? attributes.getResponse().getStatus() : 0;
            if (queryString != null) {
                log.info("{} {} {}?{} {}", responseStatus, requestMethod, requestUri, queryString,
                        System.currentTimeMillis() - startTime);
            } else {
                log.info("{} {} {} {}", responseStatus, requestMethod, requestUri,
                        System.currentTimeMillis() - startTime);
            }
            return result;
        } catch (Throwable ex) {
            int responseStatus = attributes != null && attributes.getResponse() != null ? attributes.getResponse().getStatus() : 500;
            if (queryString != null) {
                log.error("{} {} {}?{} {} Exception: {}", responseStatus, requestMethod, requestUri, queryString,
                        System.currentTimeMillis() - startTime, ex.getMessage(), ex);
            } else if (attributes != null) {
                log.error("{} {} {} {} Exception: {}", responseStatus, requestMethod, requestUri,
                        System.currentTimeMillis() - startTime, ex.getMessage(), ex);
            } else {
                log.error("Exception at {} with message: {}", joinPoint, ex.getMessage(), ex);
            }
            throw ex;
        }
    }
}