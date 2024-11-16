package com.java_e_wallet.e_wallet_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java_e_wallet.e_wallet_service.dto.LoginRequestDTO;
import com.java_e_wallet.e_wallet_service.dto.ResponseDTO;
import com.java_e_wallet.e_wallet_service.dto.TokenResponseDTO;
import com.java_e_wallet.e_wallet_service.dto.UserRegistrationDTO;
import com.java_e_wallet.e_wallet_service.dto.UserResponseDTO;
import com.java_e_wallet.e_wallet_service.model.Token;
import com.java_e_wallet.e_wallet_service.model.User;
import com.java_e_wallet.e_wallet_service.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {
    
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseDTO Register(@RequestBody @Valid UserRegistrationDTO newUser) {
        User user = authService.RegisterUser(newUser);        

        return new ResponseDTO(HttpStatus.CREATED.value(), "register success", new UserResponseDTO(user));
    }

    @PostMapping("/login")
    public ResponseDTO Login(@RequestBody @Valid LoginRequestDTO loginRequest) {
        Token token = authService.Login(loginRequest);

        return new ResponseDTO(HttpStatus.OK.value(), "login success", new TokenResponseDTO(token));
    }

}
