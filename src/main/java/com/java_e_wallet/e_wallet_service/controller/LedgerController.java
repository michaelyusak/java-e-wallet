package com.java_e_wallet.e_wallet_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java_e_wallet.e_wallet_service.dto.ResponseDTO;
import com.java_e_wallet.e_wallet_service.exception.AppException;
import com.java_e_wallet.e_wallet_service.model.CustomUserDetails;
import com.java_e_wallet.e_wallet_service.service.LedgerService;

@RestController
@RequestMapping(value = "/ledgers")
public class LedgerController {

    private final LedgerService ledgerService;

    @Autowired
    public LedgerController(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @GetMapping()
    public ResponseDTO getUserWallet() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new AppException(HttpStatus.FORBIDDEN.value(), "unauthenticated request", "login and try again");
        }

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return ledgerService.getUserLedgers(user.getId());
    }

}
