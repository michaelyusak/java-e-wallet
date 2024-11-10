package com.java_e_wallet.e_wallet_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.java_e_wallet.e_wallet_service.model.Balance;

@Repository
public interface BalanceRepo extends JpaRepository<Balance, Long> {
    
    @Query(value = "SELECT balance_id, wallet_id, asset, amount, frozen, created_at, updated_at, deleted_at FROM balances WHERE wallet_id = ?1 AND deleted_at = 0", nativeQuery = true)
    List<Balance> getBalancesByWalletId(Long walletId);
}
