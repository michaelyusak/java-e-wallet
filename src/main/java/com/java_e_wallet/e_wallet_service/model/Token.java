package com.java_e_wallet.e_wallet_service.model;

import lombok.Getter;

@Getter
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
}
