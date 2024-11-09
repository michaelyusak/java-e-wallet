package com.java_e_wallet.e_wallet_service.repository;

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
    void CreateWallet(Long userId, Long now);
}
