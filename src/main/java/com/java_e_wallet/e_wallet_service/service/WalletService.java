package com.java_e_wallet.e_wallet_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.java_e_wallet.e_wallet_service.exception.AppException;
import com.java_e_wallet.e_wallet_service.model.Balance;
import com.java_e_wallet.e_wallet_service.model.Wallet;
import com.java_e_wallet.e_wallet_service.repository.BalanceRepo;
import com.java_e_wallet.e_wallet_service.repository.WalletRepo;

import jakarta.transaction.Transactional;

@Service
public class WalletService {
    private final WalletRepo walletRepo;
    private final BalanceRepo balanceRepo;

    @Autowired
    public WalletService(
            WalletRepo walletRepo,
            BalanceRepo balanceRepo) {
        this.walletRepo = walletRepo;
        this.balanceRepo = balanceRepo;
    }

    public Optional<Wallet> getUserWallet(Long userId) {
        return walletRepo.getWalletByUserId(userId);
    }

    public List<Balance> getUserBalance(Long userId, String walletNumber) {
        Optional<Wallet> wlt = walletRepo.getWalletByWalletNumber(walletNumber);
        if (!wlt.isPresent()) {
            throw new AppException(HttpStatus.FORBIDDEN.value(), "wallet not found", null);
        }

        Wallet wallet = wlt.get();

        if (wallet.getUserId() != userId) {
            throw new AppException(HttpStatus.FORBIDDEN.value(), "unauthorized", null);
        }

        return balanceRepo.getBalancesByWalletId(wallet.getWalletId());
    }

    @Transactional
    public void transferAsset(Long userId, String recipientWalletNumber, String asset, Double amount) {
        Optional<Wallet> senderWlt = walletRepo.getWalletByUserId(userId);
        if (!senderWlt.isPresent()) {
            throw new AppException(HttpStatus.FORBIDDEN.value(), "sender wallet not found", null);
        }

        Wallet senderWallet = senderWlt.get();

        if (senderWallet.getUserId() != userId) {
            throw new AppException(HttpStatus.FORBIDDEN.value(), "unauthorized", null);
        }

        Optional<Wallet> recipientWlt = walletRepo.getWalletByWalletNumber(recipientWalletNumber);
        if (!recipientWlt.isPresent()) {
            throw new AppException(HttpStatus.NOT_FOUND.value(), "recipient wallet not found", null);
        }

        Optional<Balance> senderBln = balanceRepo.getAssetBalanceByWalletId(senderWallet.getWalletId(), asset);
        if (!senderBln.isPresent()) {
            throw new AppException(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    String.format("insufficient %s balance", asset), null);
        }

        Balance senderBalance = senderBln.get();

        if (senderBalance.getAmount() < amount) {
            throw new AppException(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    String.format("insufficient %s balance", asset), null);
        }

        

    }
}
