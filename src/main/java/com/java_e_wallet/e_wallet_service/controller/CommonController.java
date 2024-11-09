package com.java_e_wallet.e_wallet_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java_e_wallet.e_wallet_service.dto.ResponseDTO;

@RestController
public class CommonController {

    @GetMapping("/health")
    public ResponseDTO getHealth() {
        return new ResponseDTO(HttpStatus.OK.value(), "ok", null);
    }
}
