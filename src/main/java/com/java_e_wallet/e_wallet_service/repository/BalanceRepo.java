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
    void freezeBalance(Double amount, Double frozen, Long now, Long balanceId);

    default void freezeBalance(Double amount, Double frozen, Long balanceId) {
        freezeBalance(amount, frozen, Instant.now().toEpochMilli(), balanceId);
    }

    @Query(value = "SELECT balance_id, wallet_id, asset, amount, frozen, created_at, updated_at, deleted_at FROM balances WHERE wallet_id = ?1 AND asset = ?2 AND deleted_at = 0", nativeQuery = true)
    Optional<Balance> getAssetBalanceByWalletId(Long walletId, String asset);
}
