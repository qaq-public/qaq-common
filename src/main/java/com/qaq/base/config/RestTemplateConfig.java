package com.qaq.base.config;


import java.io.IOException;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.client.RestTemplate;

import com.qaq.base.component.HttpUtil;

import lombok.extern.slf4j.Slf4j;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .interceptors(new CustomClientHttpRequestInterceptor())
                .build();
    }

    @Bean
    public HttpUtil httpUtil() {
        return new HttpUtil();
    }

    @Slf4j
    static class CustomClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
        @Override
        @NonNull
        public ClientHttpResponse intercept(HttpRequest request, @NonNull byte[] bytes, @NonNull ClientHttpRequestExecution execution) throws IOException {

            ClientHttpResponse response = execution.execute(request, bytes);
            log.info("{} {} {}",response.getStatusCode(), request.getMethod(), request.getURI());

            return response;
        }
    }

}