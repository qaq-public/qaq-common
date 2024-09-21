package com.qaq.base.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse<T> {
    private int code;
    private T data;
    private String message;

    public ApiResponse(T data, String message) {
        this.code = 0;
        this.data = data;
        this.message = message;
    }

    public ApiResponse(T data) {
        this.code = 0;
        this.data = data;
        this.message = "";
    }

}
