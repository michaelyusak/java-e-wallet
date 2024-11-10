package com.java_e_wallet.e_wallet_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.java_e_wallet.e_wallet_service.model.CustomUserDetails;
import com.java_e_wallet.e_wallet_service.model.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query(value = "SELECT user_id, user_name, user_email, user_password, created_at, updated_at, deleted_at FROM users WHERE user_email = ?1 AND deleted_at = 0", nativeQuery = true)
    Optional<User> getUserByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO users (user_email, user_name, user_password, created_at, updated_at) VALUES (?1, ?2, ?3, ?4, ?4)", nativeQuery = true)
    void registerUser(String email, String name, String password, Long now);

    @Query(value = "SELECT user_id, user_name, user_email, user_password, created_at, updated_at, deleted_at FROM users WHERE user_id = ?1", nativeQuery = true)
    Optional<User> getUserById(Long userId);

    @Query(value = "SELECT user_name, user_password WHERE email = ?1 AND deleted_at = 0", nativeQuery = true)
    Optional<CustomUserDetails> getDetailByEmail(String email);
}
