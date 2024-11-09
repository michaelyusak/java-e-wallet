package com.java_e_wallet.e_wallet_service.service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.java_e_wallet.e_wallet_service.config.Config;
import com.java_e_wallet.e_wallet_service.dto.LoginRequestDTO;
import com.java_e_wallet.e_wallet_service.dto.UserRegistrationDTO;
import com.java_e_wallet.e_wallet_service.exception.AppException;
import com.java_e_wallet.e_wallet_service.helper.HashHelper;
import com.java_e_wallet.e_wallet_service.helper.JWTHelper;
import com.java_e_wallet.e_wallet_service.model.Token;
import com.java_e_wallet.e_wallet_service.model.User;
import com.java_e_wallet.e_wallet_service.repository.UserRepo;
import com.java_e_wallet.e_wallet_service.repository.WalletRepo;

import jakarta.transaction.Transactional;


@Service
public class AuthService {
    private final UserRepo userRepo;
    private final WalletRepo walletRepo;

    @Autowired
    public AuthService(UserRepo userRepo, WalletRepo walletRepo) {
        this.userRepo = userRepo;
        this.walletRepo = walletRepo;
    }


    @Transactional
    public User RegisterUser(UserRegistrationDTO newUser) {
        Optional<User> existingUser = userRepo.getUserByEmail(newUser.getEmail());
        if (existingUser.isPresent()) {
            throw new AppException(HttpStatus.BAD_REQUEST.value(), "email is already taken", "");
        }

        String hash = HashHelper.hash(newUser.getPassword());

        userRepo.registerUser(newUser.getEmail(), newUser.getName(), hash, Instant.now().toEpochMilli());

        Optional<User> user = userRepo.getUserByEmail(newUser.getEmail());
        if (!user.isPresent()) {
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "something went wrong", "");
        }

        User registeredUser = user.get();

        Long now = Instant.now().toEpochMilli();

        walletRepo.createWallet(registeredUser.getUserId(), now);

        return registeredUser;
    }

    public Token Login(LoginRequestDTO loginRequest) {
        Optional<User> existingUser = userRepo.getUserByEmail(loginRequest.getEmail());
        if (!existingUser.isPresent()) {
            throw new AppException(HttpStatus.FORBIDDEN.value(), "your email is unregistered", "try register first");
        }

        User user = existingUser.get();

        if (!HashHelper.compare(loginRequest.getPassword(), user.getUserPassword())) {
            throw new AppException(HttpStatus.FORBIDDEN.value(), "wrong email or password", "");
        }

        Long userId = user.getUserId();

        String accessToken = JWTHelper.generateToken(userId, 1);
        String refreshToken = JWTHelper.generateToken(userId, 2);
        Config config = Config.getConfigInstance();

        Token token = new Token(accessToken, config.getAccessTokenTTL(), refreshToken, config.getRefreshTokenTTL(), userId);

        return token;
    }
}
