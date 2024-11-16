package com.java_e_wallet.e_wallet_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.java_e_wallet.e_wallet_service.model.Wallet;

public class WalletResponseDTO {
    @JsonProperty("wallet_id")
    private Long walletId;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("wallet_number")
    private String walletNumber;

    @JsonProperty("created_at")
    private Long createdAt;

    @JsonProperty("updated_at")
    private Long updatedAt;

    public WalletResponseDTO(Wallet wallet) {
        this.walletId = wallet.getWalletId();
        this.userId = wallet.getUserId();
        this.walletNumber = wallet.getWalletNumber();
        this.createdAt = wallet.getCreatedAt();
        this.updatedAt = wallet.getUpdatedAt();
    }
}
