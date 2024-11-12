package com.java_e_wallet.e_wallet_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "transactions")
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "sender_wallet_id")
    private Long senderWalletId;

    @Column(name = "recipient_wallet_id")
    private Long recipientWalletId;

    @Column(name = "asset")
    private String asset;

    @Column(name = "amount")
    private double amount;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Column(name = "updated_at", nullable = false)
    private Long updatedAt;

    @Column(name = "deleted_at", nullable = false)
    private Long deletedAt = 0L;

    public Transaction() {
    }
}
