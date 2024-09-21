package com.qaq.base.filter;

import java.io.IOException;

import org.springframework.core.Ordered;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingFilter implements Filter, Ordered {

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String method = request.getMethod();
        String path = request.getRequestURI();
        String queryParams = request.getQueryString();

        filterChain.doFilter(request, response);
        Long endTime = System.currentTimeMillis();
        Long duration = endTime - startTime;
        if (queryParams != null) {
            log.info("{} {} {}?{} {}", response.getStatus(), method, path, queryParams, duration);
        } else {
            log.info("{} {} {} {}", response.getStatus(), method, path, duration);
        }
    }
}
