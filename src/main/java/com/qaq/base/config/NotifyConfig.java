package com.qaq.base.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qaq.base.component.HttpUtil;
import com.qaq.base.component.NotifyUtil;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "qaq.notify")
public class NotifyConfig {

    @Setter
    @Getter
    private String url = "http://notify";

    @Setter
    @Getter
    private String message = "http://notify/messages";

    @Setter
    @Getter
    private String notifyPackage = "http://notify/messages/packages";

    @Setter
    @Getter
    private String userMap = "http://notify/organization/userMap";

    @Setter
    @Getter
    private String user = "http://notify/organization/get/userinfo?userid=%s";

    @Bean
    public NotifyUtil notifyUtil(HttpUtil httpUtil) {
        return new NotifyUtil(httpUtil, message, notifyPackage, userMap, user);
    }
    
}
