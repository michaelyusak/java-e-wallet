package com.java_e_wallet.e_wallet_service.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java_e_wallet.e_wallet_service.dto.BalanceResponseDTO;
import com.java_e_wallet.e_wallet_service.dto.ResponseDTO;
import com.java_e_wallet.e_wallet_service.dto.WalletResponseDTO;
import com.java_e_wallet.e_wallet_service.exception.AppException;
import com.java_e_wallet.e_wallet_service.model.Balance;
import com.java_e_wallet.e_wallet_service.model.CustomUserDetails;
import com.java_e_wallet.e_wallet_service.model.Wallet;
import com.java_e_wallet.e_wallet_service.service.WalletService;

@RestController
@RequestMapping(path = "/wallet")
public class WalletController {
    private final WalletService walletService;

    public WalletController(
            WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping()
    public ResponseDTO getUserWallet() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new AppException(HttpStatus.FORBIDDEN.value(), "unauthenticated request", "login and try again");
        }

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        Optional<Wallet> wallet = walletService.getUserWallet(user.getId());

        System.out.println(user.getEmail());

        return new ResponseDTO(HttpStatus.OK.value(), "ok",
                wallet.isPresent() ? new WalletResponseDTO(wallet.get()) : null);
    }

    @GetMapping(path = "/{wallet_number}/balance")
    public ResponseDTO getUserBalance(@PathVariable("wallet_number") String walletNumber) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new AppException(HttpStatus.FORBIDDEN.value(), "unauthenticated request", "login and try again");
        }

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        List<Balance> balances = walletService.getUserBalance(user.getId(), walletNumber);

        return new ResponseDTO(HttpStatus.OK.value(), "ok",
                balances.stream().map(BalanceResponseDTO::new).collect(Collectors.toList()));
    }
}
