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
import com.java_e_wallet.e_wallet_service.repository.TransactionRepo;
import com.java_e_wallet.e_wallet_service.repository.WalletRepo;

import jakarta.transaction.Transactional;

@Service
public class WalletService {
    private final WalletRepo walletRepo;
    private final BalanceRepo balanceRepo;
    private final TransactionRepo transactionRepo;

    @Autowired
    public WalletService(
            WalletRepo walletRepo,
            BalanceRepo balanceRepo,
            TransactionRepo transactionRepo) {
        this.walletRepo = walletRepo;
        this.balanceRepo = balanceRepo;
        this.transactionRepo = transactionRepo;
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
    public Balance transferAsset(Long userId, String recipientWalletNumber, String asset, double amount) {
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

        Wallet recipientWallet = recipientWlt.get();

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

        balanceRepo.freezeBalance(senderBalance.getAmount() - amount, senderBalance.getFrozen() + amount, userId);

        transactionRepo.addTransaction(senderWallet.getWalletId(), recipientWallet.getWalletId(), asset, amount);

        Optional<Balance> recipientBln = balanceRepo.getAssetBalanceByWalletId(recipientWallet.getUserId(), asset);
        if (!recipientBln.isPresent()) {
            balanceRepo.createBalance(recipientWallet.getWalletId(), asset);

            recipientBln = balanceRepo.getAssetBalanceByWalletId(recipientWallet.getUserId(), asset);
            if (!recipientBln.isPresent()) {
                throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "internal server error", null);
            }
        }

        Balance recipientBalance = recipientBln.get();

        balanceRepo.addBalance(recipientBalance.getAmount() + amount, recipientBalance.getBalanceId());

        balanceRepo.unfreezeBalance(senderBalance.getFrozen() - amount, userId);

        return new Balance(senderBalance.getWalletId(), senderBalance.getAsset(), senderBalance.getAmount() - amount,
                senderBalance.getFrozen());
    }

    @Transactional
    public Balance createBalance(Long userId, String asset) {
        Optional<Wallet> wlt = walletRepo.getWalletByUserId(userId);
        if (!wlt.isPresent()) {
            throw new AppException(HttpStatus.FORBIDDEN.value(), "wallet not found", null);
        }

        Wallet wallet = wlt.get();

        Optional<Balance> bln = balanceRepo.getAssetBalanceByWalletId(wallet.getWalletId(), asset);
        if (bln.isPresent()) {
            throw new AppException(HttpStatus.CONFLICT.value(), "balance already exist", null);
        }

        balanceRepo.createBalance(wallet.getWalletId(), asset);

        bln = balanceRepo.getAssetBalanceByWalletId(wallet.getWalletId(), asset);
        if (!bln.isPresent()) {
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "internal server error", null);
        }

        return bln.get();
    }
}
