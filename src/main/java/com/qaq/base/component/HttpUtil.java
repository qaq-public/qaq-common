package com.qaq.base.component;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class HttpUtil {
    
    final private RestTemplate restTemplate = new RestTemplate();

    public <T, B> T exchange(String url, HttpMethod method, B body, Class<T> responseType) {

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<B> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<T> resultEntity = restTemplate.exchange(url, method, requestEntity, responseType);

        return resultEntity.getBody();
    }
    

    public <T, B> T exchange(String url, HttpMethod method, B body, Class<T> responseType, String authorization) {

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authorization);

        HttpEntity<B> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<T> resultEntity = restTemplate.exchange(url, method, requestEntity, responseType);

        return resultEntity.getBody();
    }

    public <T, B> T exchange(String url, HttpMethod method, B body, ParameterizedTypeReference<T> responseType) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<B> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<T> resultEntity = restTemplate.exchange(url, method, requestEntity, responseType);

        return resultEntity.getBody();
    }

    public <T, B> T exchange(String url, HttpMethod method, B body, HttpHeaders headers, ParameterizedTypeReference<T> responseType) {
        // 请求体
        if (headers == null) {
            headers = new HttpHeaders();
        }
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 发送请求
        HttpEntity<B> entity = new HttpEntity<>(body, headers);
        ResponseEntity<T> resultEntity = restTemplate.exchange(url, method, entity, responseType);
        return resultEntity.getBody();
    }

    public <T, B> T exchange(String url, HttpMethod method, B body, ParameterizedTypeReference<T> responseType, String authorization) {

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authorization);

        HttpEntity<B> requestEntity = new HttpEntity<>(body, headers);
        
        ResponseEntity<T> resultEntity = restTemplate.exchange(url, method, requestEntity, responseType);

        return resultEntity.getBody();
    }

}
