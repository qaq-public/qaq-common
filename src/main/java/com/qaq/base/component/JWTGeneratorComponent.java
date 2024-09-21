package com.qaq.base.component;

import java.security.interfaces.RSAPrivateKey;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.lark.oapi.service.authen.v1.model.GetUserInfoRespBody;

import lombok.extern.slf4j.Slf4j;

/**
 * 生成jwt token， 包含name、avatar以及email字段， exp字段由tokenExpTimeInMinute控制
 */
@Slf4j
@Component
public class JWTGeneratorComponent {

    private Integer tokenExpTimeInDay;
    private RSAPrivateKey privateKey;

    public JWTGeneratorComponent(RSAPrivateKey privateKey, Integer tokenExpTimeInDay) {
        this.privateKey = privateKey;
        this.tokenExpTimeInDay = tokenExpTimeInDay;
    }

    public String generate(GetUserInfoRespBody user) {
        String salt = UUID.randomUUID().toString().trim().replaceAll("-", "");
        String token = "";
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "RS256");
        header.put("typ", "JWT");
        try {
            // 只用签名,提供privateKey即可
            Algorithm algorithm = Algorithm.RSA256(null, this.privateKey);
            Date exp = getExpDate();
            user.getUserId();
            token = JWT.create()
                    .withExpiresAt(exp)
                    .withClaim("name", user.getName())
                    .withClaim("avatar", user.getAvatarUrl().trim())
                    .withClaim("email", user.getEmail() == null ? "" : user.getEmail())
                    .withClaim("userid", user.getUserId() == null ? "" : user.getUserId())
                    .withClaim("openid", user.getOpenId().trim())
                    .withClaim("salt", salt)
                    .withHeader(header)
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            e.printStackTrace();
        }
        if (token.isBlank()) {
            log.error("the jwt token is null, user: {} ", user);
            throw  new RuntimeException("JWT Token错误");
        }
        return token;
    }

    public String generate(com.lark.oapi.service.contact.v3.model.User user) {
        String salt = UUID.randomUUID().toString().trim().replaceAll("-", "");
        String token = "";
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "RS256");
        header.put("typ", "JWT");
        try {
            // 只用签名,提供privateKey即可
            Algorithm algorithm = Algorithm.RSA256(null, this.privateKey);
            Date exp = getExpDate();
            token = JWT.create()
                    .withExpiresAt(exp)
                    .withClaim("name", user.getName())
                    .withClaim("avatar", user.getAvatar().getAvatar72().trim())
                    .withClaim("email", user.getEmail())
                    .withClaim("userid", user.getUserId().trim())
                    .withClaim("openid", user.getOpenId().trim())
                    .withClaim("salt", salt)
                    .withHeader(header)
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            e.printStackTrace();
        }
        if (token.isBlank()) {
            log.error("the jwt token is null, user: {} ", user);
            throw  new RuntimeException("JWT Token错误");
        }
        return token;
    }

    private Date getExpDate() {
        Date exp = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(exp);
        calendar.add(Calendar.DATE, tokenExpTimeInDay);
        exp = calendar.getTime();
        return exp;
    }

}
