package com.java_e_wallet.e_wallet_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class TransactionRequestDTO {

    @JsonProperty("recipient_wallet_number")
    private String recipientWalletNumber;

    @JsonProperty("asset")
    private String asset;

    @JsonProperty("amount")
    private double amount;
}
