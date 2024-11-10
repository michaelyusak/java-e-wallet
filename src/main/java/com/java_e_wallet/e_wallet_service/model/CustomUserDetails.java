package com.java_e_wallet.e_wallet_service.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails {
    @Column(name = "id")
    private final Long id;

    @Column(name = "name")
    private final String username;

    @Column(name = "email")
    private final String email;

    @Column(name = "password")
    private final String password;

    public CustomUserDetails(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public CustomUserDetails(User user) {
        this.id = user.getUserId();
        this.username = user.getUserName();
        this.email = user.getUserEmail();
        this.password = user.getUserPassword();
    }

    public CustomUserDetails(CustomUserDetails user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
