package com.java_e_wallet.e_wallet_service.repository;

import java.time.Instant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.java_e_wallet.e_wallet_service.model.Transaction;

import jakarta.transaction.Transactional;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO transactions (sender_wallet_id, recipient_wallet_id, asset, amount, created_at, updated_at) VALUES (?1, ?2, ?3, ?4, ?5, ?5)", nativeQuery = true)
    void addTransaction(Long senderWalletId, Long recipientWalletId, String asset, double amount, Long now);

    default void addTransaction(Long senderWalletId, Long recipientWalletId, String asset, double amount) {
        addTransaction(senderWalletId, recipientWalletId, asset, amount, Instant.now().toEpochMilli());
    }
}
