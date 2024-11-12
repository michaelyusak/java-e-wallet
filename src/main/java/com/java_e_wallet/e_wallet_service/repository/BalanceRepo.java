package com.java_e_wallet.e_wallet_service.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.java_e_wallet.e_wallet_service.model.Balance;

import jakarta.transaction.Transactional;

@Repository
public interface BalanceRepo extends JpaRepository<Balance, Long> {

    @Query(value = "SELECT balance_id, wallet_id, asset, amount, frozen, created_at, updated_at, deleted_at FROM balances WHERE wallet_id = ?1 AND deleted_at = 0", nativeQuery = true)
    List<Balance> getBalancesByWalletId(Long walletId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE balances SET amount = ?1, frozen = ?2, updated_at = ?3 WHERE balance_id = ?4", nativeQuery = true)
    void freezeBalance(double newAmount, double newFrozen, Long now, Long balanceId);

    default void freezeBalance(double amount, double frozen, Long balanceId) {
        freezeBalance(amount, frozen, Instant.now().toEpochMilli(), balanceId);
    }

    @Query(value = "SELECT balance_id, wallet_id, asset, amount, frozen, created_at, updated_at, deleted_at FROM balances WHERE wallet_id = ?1 AND asset = ?2 AND deleted_at = 0", nativeQuery = true)
    Optional<Balance> getAssetBalanceByWalletId(Long walletId, String asset);

    @Transactional
    @Modifying
    @Query(value = "UPDATE balances SET frozen = ?1, updated_at = ?2 WHERE balance_id = ?3", nativeQuery = true)
    void unfreezeBalance(double newFrozen, Long now, Long balanceId);

    default void unfreezeBalance(double frozen, Long balanceId) {
        unfreezeBalance(frozen, Instant.now().toEpochMilli(), balanceId);
    }

    @Transactional
    @Modifying
    @Query(value = "UPDATE balances SET amount = ?1, updated_at = ?2 WHERE balance_id = ?3", nativeQuery = true)
    void addBalance(double newAmount, Long now, Long balanceId);

    default void addBalance(double newAmount, Long balanceId) {
        addBalance(newAmount, Instant.now().toEpochMilli(), balanceId);
    }

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO balances (wallet_id, asset, created_at, updated_at) VALUES (?1, ?2, ?3, ?3)", nativeQuery = true)
    void createBalance(Long walletId, String asset, Long now);

    default void createBalance(Long walletId, String asset) {
        createBalance(walletId, asset, Instant.now().toEpochMilli());
    }
}
