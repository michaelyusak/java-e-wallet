package com.java_e_wallet.e_wallet_service.model;

import lombok.Getter;

@Getter
public class TransactionResult {
    private Balance balance;

    private TransactionSummary summary;

    public TransactionResult(Balance balance, TransactionSummary summary) {
        this.balance = balance;
        this.summary = summary;
    }
}
