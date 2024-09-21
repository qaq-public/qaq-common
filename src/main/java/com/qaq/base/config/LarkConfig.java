package com.qaq.base.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lark.oapi.Client;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Configuration
@ConfigurationProperties(prefix = "feishu")
public class LarkConfig {

    @Setter
    @Getter
    private String appId;

    @Setter
    @Getter
    private String appSecret;

    @Qualifier("LarkClient")
    @Bean
    public Client client() {
        log.debug("id: " + this.appId);
        log.debug("secret: " + this.appSecret);
        var client =  Client.newBuilder(this.appId.trim(), this.appSecret.trim()).logReqAtDebug(true).build();
        log.debug("build success");
        return client;
    }

}
