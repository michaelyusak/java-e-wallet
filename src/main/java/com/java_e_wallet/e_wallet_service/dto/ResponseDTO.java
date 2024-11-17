package com.java_e_wallet.e_wallet_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class ResponseDTO {
    @JsonProperty("status_code")
    private int statusCode;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public ResponseDTO() {
    }

    public ResponseDTO(
            int statusCode,
            String message,
            Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
}
