package com.qaq.base.intercepter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qaq.base.enums.GatewayHeaderEnum;
import com.qaq.base.exception.UnAuthorizedException;
import com.qaq.base.model.Auth;
import com.qaq.base.model.login.Token;
import com.qaq.base.model.uniauth.AuthResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Base64;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    private static final Base64.Decoder BASE64_URL_DECODER = Base64.getUrlDecoder();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        var tokenStr = request.getHeader(GatewayHeaderEnum.X_Gateway_Token.getHeaderName());
        var permissionStr = request.getHeader(GatewayHeaderEnum.X_Gateway_Permission.getHeaderName());
        if (tokenStr == null || tokenStr.trim().isEmpty()) {
            log.error("Token header is missing or empty");
            throw new UnAuthorizedException("The auth token from gateway header is missing");
        }
        var auth = createAuth(tokenStr, permissionStr);
        request.setAttribute("auth", auth);
        return true;
    }

    private Auth createAuth(@NonNull String tokenStr, String permissionStr) {
        Auth auth = new Auth();
        auth.setToken(parseToken(tokenStr));
        if (permissionStr != null && !permissionStr.trim().isEmpty()) {
            auth.setAuthResult(parseAuthResult(permissionStr));
        } else {
            log.info("AuthResult 可以为空");
        }
        return auth;
    }

    private Token parseToken(String tokenStr) {
        try {
            String decodedToken = new String(BASE64_URL_DECODER.decode(tokenStr));
            return OBJECT_MAPPER.readValue(decodedToken, Token.class);
        } catch (Exception e) {
            log.error("Failed to decode and parse token from header. tokenStr: {}", tokenStr, e);
            throw new UnAuthorizedException("Failed to decode the auth token from gateway header");
        }
    }

    private AuthResult parseAuthResult(String permissionStr) {
        try {
            String decodedPermission = new String(BASE64_URL_DECODER.decode(permissionStr));
            return OBJECT_MAPPER.readValue(decodedPermission, AuthResult.class);
        } catch (Exception e) {
            log.error("Failed to decode and parse permission from header. permissionStr: {} ", permissionStr, e);
            throw new UnAuthorizedException("Failed to decode the auth permission from gateway header");
        }
    }
}
