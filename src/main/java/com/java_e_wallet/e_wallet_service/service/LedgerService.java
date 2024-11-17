package com.java_e_wallet.e_wallet_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java_e_wallet.e_wallet_service.adaptor.LedgerServiceAdaptor;
import com.java_e_wallet.e_wallet_service.dto.ResponseDTO;

@Service
public class LedgerService {
    
    @Autowired
    public LedgerService() {
    }

    public ResponseDTO getUserLedgers(Long userId) {
        return LedgerServiceAdaptor.getUserLedgers(userId);
    }
}
