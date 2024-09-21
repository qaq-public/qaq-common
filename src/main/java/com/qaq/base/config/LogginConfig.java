package com.qaq.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qaq.base.filter.LoggingFilter;

@Configuration
public class LogginConfig {

    @Bean
    public LoggingFilter LoggingFilter() {
        return new LoggingFilter();
    }
}
