package com.java_e_wallet.e_wallet_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.java_e_wallet.e_wallet_service.model.Balance;

public class BalanceResponseDTO {
    
    @JsonProperty("balance_id")
    private Long balanceId;

    @JsonProperty("wallet_id")
    private Long walletId;

    @JsonProperty("asset")
    private String asset;

    @JsonProperty("amount")
    private double amount;

    @JsonProperty("frozen")
    private double frozen;

    public BalanceResponseDTO(Balance balance) {
        this.balanceId = balance.getBalanceId();
        this.walletId = balance.getWalletId();
        this.asset = balance.getAsset();
        this.amount = balance.getAmount();
        this.frozen = balance.getFrozen();
    }
}
