package com.java_e_wallet.e_wallet_service.model;

public class Token {
    private String AccessToken;
    private Long AccessTokenEXP;
    private String RefreshToken;
    private Long RefreshTokenEXP;
    private Long UserId;

    public Token(String accessToken, Long accessTokenEXP, String refreshToken, Long refreshTokenEXP, Long userId) {
        this.AccessToken = accessToken;
        this.AccessTokenEXP = accessTokenEXP;
        this.RefreshToken = refreshToken;
        this.RefreshTokenEXP = refreshTokenEXP;
        this.UserId = userId;
    }

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String accessToken) {
        this.AccessToken = accessToken;
    }

    public Long getAccessTokenEXP() {
        return AccessTokenEXP;
    }

    public void setAccessTokenEXP(Long accessTokenEXP) {
        this.AccessTokenEXP = accessTokenEXP;
    }

    public String getRefreshToken() {
        return RefreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.RefreshToken = refreshToken;
    }

    public Long getRefreshTokenEXP() {
        return RefreshTokenEXP;
    }

    public void setRefreshTokenEXP(Long refreshTokenEXP) {
        this.RefreshTokenEXP = refreshTokenEXP;
    }

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        this.UserId = userId;
    }
}
