package com.java_e_wallet.e_wallet_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.java_e_wallet.e_wallet_service.model.Token;

public class TokenResponseDTO {
    @JsonProperty("access_token")
    private String AccessToken;

    @JsonProperty("access_token_exp")
    private Long AccessTokenEXP;

    @JsonProperty("refresh_token")
    private String RefreshToken;

    @JsonProperty("refresh_token_exp")
    private Long RefreshTokenEXP;

    @JsonProperty("user_id")
    private Long UserId;

    public TokenResponseDTO(Token token) {
        this.AccessToken = token.getAccessToken();
        this.AccessTokenEXP = token.getAccessTokenEXP();
        this.RefreshToken = token.getRefreshToken();
        this.RefreshTokenEXP = token.getRefreshTokenEXP();
        this.UserId = token.getUserId();
    }
}
