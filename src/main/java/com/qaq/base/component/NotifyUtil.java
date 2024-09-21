package com.qaq.base.component;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import com.lark.oapi.service.contact.v3.model.User;
import com.qaq.base.model.Message;
import com.qaq.base.response.ApiResponse;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotifyUtil {

    private com.qaq.base.component.HttpUtil httpUtil;
    private String messageUrl;
    private String notifyPackageUrl;
    private String userMapUrl;
    private String userUrl;

    public NotifyUtil(com.qaq.base.component.HttpUtil httpUtil, String messageUrl, String notifyPackageUrl, String userMapUrl,
                      String userUrl) {
        this.httpUtil = httpUtil;
        this.messageUrl = messageUrl;
        this.notifyPackageUrl = notifyPackageUrl;
        this.userMapUrl = userMapUrl;
        this.userUrl = userUrl;
    }

    public ApiResponse<List<Object>> send(Message message) {
        var responseType = new ParameterizedTypeReference<ApiResponse<List<Object>>>() {
        };
        var res = httpUtil.exchange(messageUrl, HttpMethod.POST, message, responseType);
        return res;
    }

    public ApiResponse<List<Object>> notifyLibai(String content) {

        return this.send(Message.builder()
                .content(content)
                .msg_type("post")
                .touser(List.of("lifajin@pandadagames.com"))
                .build());
    }

    public ApiResponse<Map<String, User>> getUserMap() {
        var responseType = new ParameterizedTypeReference<ApiResponse<Map<String, User>>>() {
        };
        return httpUtil.exchange(userMapUrl, HttpMethod.GET, null, responseType);

    }

    public ApiResponse<User> getUser(String userId) {
        var responseType = new ParameterizedTypeReference<ApiResponse<User>>() {
        };
        return httpUtil.exchange(String.format(userUrl, userId), HttpMethod.GET, null, responseType);
    }

    public void sendMessageToNotify(List<String> toUsers, String downUrl, String desc, String title) {

        var packageMessage = PackageMessage.builder()
                .url(downUrl)
                .description(desc)
                .title(title)
                .touser(toUsers)
                .build();
        var response = httpUtil.exchange(notifyPackageUrl, HttpMethod.POST, packageMessage, ApiResponse.class);
        if (response.getCode() == 0) {
            log.debug("发送消息成功: {}", response);
        } else {
            log.error("发送消息失败: {}", response);
        }
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class PackageMessage {

        @NotBlank
        private String url;
        @NotBlank
        private String description;
        @NotBlank
        private String title;
        private String fromuser;
        @NotEmpty
        private List<String> touser;
    }
}
