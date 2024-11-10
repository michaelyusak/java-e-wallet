package com.java_e_wallet.e_wallet_service.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java_e_wallet.e_wallet_service.model.Wallet;
import com.java_e_wallet.e_wallet_service.repository.BalanceRepo;
import com.java_e_wallet.e_wallet_service.repository.WalletRepo;

@Service
public class WalletService {
    private final WalletRepo walletRepo;
    private final BalanceRepo balanceRepo;

    @Autowired
    public WalletService(
        WalletRepo walletRepo,
        BalanceRepo balanceRepo
    ) {
        this.walletRepo = walletRepo;
        this.balanceRepo = balanceRepo;
    }

    public Optional<Wallet> getWalletByUserId(Long userId) {
        return walletRepo.getWalletByUserId(userId);
    }
}
