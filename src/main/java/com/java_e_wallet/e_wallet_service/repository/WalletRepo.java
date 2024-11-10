package com.java_e_wallet.e_wallet_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.java_e_wallet.e_wallet_service.model.Wallet;

import jakarta.transaction.Transactional;

@Repository
public interface WalletRepo extends JpaRepository<Wallet, Long> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO wallets (user_id, created_at, updated_at) VALUES (?1, ?2, ?2)", nativeQuery = true)
    void createWallet(Long userId, Long now);

    @Query(value = "SELECT wallet_id, wallet_number, user_id, created_at, updated_at, deleted_at FROM wallets WHERE user_id = ?1 AND deleted_at = 0", nativeQuery = true)
    Optional<Wallet> getWalletByUserId(Long userId);

    @Query(value = "SELECT wallet_id, wallet_number, user_id, created_at, updated_at, deleted_at FROM wallets WHERE wallet_number = ?1 AND deleted_at = 0", nativeQuery = true)
    Optional<Wallet> getWalletByWalletNumber(String walletNumber);
}
