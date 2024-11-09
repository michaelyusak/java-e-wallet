package com.java_e_wallet.e_wallet_service.exception;

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

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
