package com.java_e_wallet.e_wallet_service.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.java_e_wallet.e_wallet_service.exception.AppException;
import com.java_e_wallet.e_wallet_service.model.Transaction;

import jakarta.transaction.Transactional;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    
    @Transactional
    @Query(value = "INSERT INTO transactions (sender_wallet_id, recipient_wallet_id, asset, amount, created_at, updated_at) VALUES (?1, ?2, ?3, ?4, ?5, ?5) RETURNING transaction_id", nativeQuery = true)
    Optional<Integer> addTransaction(Long senderWalletId, Long recipientWalletId, String asset, double amount, Long now);

    default Long addTransaction(Long senderWalletId, Long recipientWalletId, String asset, double amount) {
        Optional<Integer> transactionId = addTransaction(senderWalletId, recipientWalletId, asset, amount, Instant.now().toEpochMilli());
        if (!transactionId.isPresent()) {
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "failed to create transaction", null);
        }

        return Long.valueOf(transactionId.get());
    }
}
