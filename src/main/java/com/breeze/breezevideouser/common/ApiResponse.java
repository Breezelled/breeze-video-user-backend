package com.breeze.breezevideouser.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author breeze
 * @date 2022/12/12 02:25
 */
@Getter
@Setter
public class ApiResponse implements Serializable {
    private Integer code;
    private String errorMsg;
    private Object data;

    private ApiResponse(int code, String errorMsg, Object data) {
        this.code = code;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public ApiResponse(){

    }

    public static ApiResponse ok() {
        return new ApiResponse(0, "", new HashMap<>());
    }

    public static ApiResponse ok(Object data) {
        return new ApiResponse(0, "", data);
    }

    public static ApiResponse error(String errorMsg) {
        return new ApiResponse(0, errorMsg, new HashMap<>());
    }

    public static ApiResponse ok(String errorMsg, Object data) {
        return new ApiResponse(0, errorMsg, data);
    }
}
