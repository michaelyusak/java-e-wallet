package com.java_e_wallet.e_wallet_service.service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.java_e_wallet.e_wallet_service.dto.UserRegistrationDTO;
import com.java_e_wallet.e_wallet_service.exception.AppException;
import com.java_e_wallet.e_wallet_service.helper.HashHelper;
import com.java_e_wallet.e_wallet_service.model.User;
import com.java_e_wallet.e_wallet_service.repository.UserRepo;

@Service
public class AuthService {
    private final UserRepo userRepo;

    @Autowired
    public AuthService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

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

        return user.get();
    }
}
