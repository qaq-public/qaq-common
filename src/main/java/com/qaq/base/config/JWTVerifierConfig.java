package com.qaq.base.config;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qaq.base.component.JWTVerifierComponent;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Setter
@Getter
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "rsa")
public class JWTVerifierConfig {

    private String publicKey;

    @Bean
    public JWTVerifierComponent jwtVerifierComponent() throws NoSuchAlgorithmException, InvalidKeySpecException {
        return new JWTVerifierComponent(publicKey);
    }
}
