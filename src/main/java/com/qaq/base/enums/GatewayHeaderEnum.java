package com.qaq.base.enums;

import lombok.Getter;

@Getter
public enum GatewayHeaderEnum {
    X_Gateway_UserId("X-Gateway-UserId",  "X-Gateway-UserId"),
    X_Gateway_UserIdType("X-Gateway-UserIdType",  "X-Gateway-UserIdType"),
    X_Gateway_Token("X-Gateway-Token",  "X-Gateway-Token"),
    X_Gateway_Permission("X-Gateway-Permission",  "X-Gateway-Permission");

    private final String headerName;
    private final String claimName;

    private GatewayHeaderEnum(String headerName, String claimName) {
        this.headerName = headerName;
        this.claimName = claimName;
    }

}

