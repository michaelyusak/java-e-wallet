package com.java_e_wallet.e_wallet_service.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java_e_wallet.e_wallet_service.model.User;
import com.java_e_wallet.e_wallet_service.repository.UserRepo;

@Service
public class UserService {
    
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public Optional<User> getUserById(Long userId) {
        return userRepo.getUserById(userId);
    }
}
