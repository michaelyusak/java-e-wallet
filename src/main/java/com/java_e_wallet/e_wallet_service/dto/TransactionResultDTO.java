package com.java_e_wallet.e_wallet_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.java_e_wallet.e_wallet_service.model.TransactionResult;

public class TransactionResultDTO {

    @JsonProperty("summary")
    TransactionSummaryDTO summary;

    @JsonProperty("balance")
    BalanceResponseDTO balance;    

    public TransactionResultDTO(TransactionResult result) {
        this.summary = new TransactionSummaryDTO(result.getSummary());
        this.balance = new BalanceResponseDTO(result.getBalance());
    }
}
