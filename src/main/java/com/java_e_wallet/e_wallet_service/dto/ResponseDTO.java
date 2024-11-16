package com.java_e_wallet.e_wallet_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ResponseDTO {
    private int status_code;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public ResponseDTO(
        int statusCode, 
        String message, 
        Object data
    ) {
        this.status_code = statusCode;
        this.message = message;
        this.data = data;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
