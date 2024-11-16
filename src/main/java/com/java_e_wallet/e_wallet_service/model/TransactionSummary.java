package com.java_e_wallet.e_wallet_service.model;

import java.time.Instant;

import lombok.Getter;

@Getter
public class TransactionSummary {

    private Long transactionId;

    private Long senderUserId;

    private Long recipientUserId;

    private String requestId;

    private String asset;

    private double amount;

    private Long createdAt = Instant.now().toEpochMilli();

    public TransactionSummary(
        Long transactionId,
        Long senderUserId,
        Long recipientUserId,
        String requestId,
        String asset,
        double amount
    ) {
        this.transactionId = transactionId;
        this.senderUserId = senderUserId;
        this.recipientUserId = recipientUserId;
        this.requestId = requestId;
        this.asset = asset;
        this.amount = amount;
    }
}
