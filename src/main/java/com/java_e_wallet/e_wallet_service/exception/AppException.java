package com.java_e_wallet.e_wallet_service.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private int statusCode;
    private String message;
    private String details;

    public AppException(int statusCode, String message, String details) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
        this.details = details;
    }
}
