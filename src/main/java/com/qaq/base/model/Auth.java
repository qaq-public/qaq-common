package com.qaq.base.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.qaq.base.model.login.Token;
import com.qaq.base.model.uniauth.AuthResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Auth {

    private Token token;
    private AuthResult authResult;

    public boolean havePermission(String permission) {
        return authResult.getPermissions().contains(permission);
    }
}
