package com.java_e_wallet.e_wallet_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.java_e_wallet.e_wallet_service.model.TransactionSummary;

public class TransactionSummaryDTO {

    @JsonProperty("transaction_id")
    private Long transactionId;

    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("asset")
    private String asset;

    @JsonProperty("amount")
    private double amount;

    @JsonProperty("created_at")
    private Long createdAt;

    public TransactionSummaryDTO(TransactionSummary summary) {
        this.transactionId = summary.getTransactionId();
        this.requestId = summary.getRequestId();
        this.asset = summary.getAsset();
        this.amount = summary.getAmount();
        this.createdAt = summary.getCreatedAt();
    }
}
