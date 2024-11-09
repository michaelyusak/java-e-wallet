package com.java_e_wallet.e_wallet_service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.java_e_wallet.e_wallet_service.helper.JWTHelper;
import com.java_e_wallet.e_wallet_service.model.User;
import com.java_e_wallet.e_wallet_service.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.IOException;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final UserService userService;

    public JWTAuthenticationFilter(
            UserService userService,
            HandlerExceptionResolver handlerExceptionResolver) {
        this.userService = userService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            try {
                filterChain.doFilter(request, response);
            } catch (Exception ex) {
                handlerExceptionResolver.resolveException(request, response, authHeader, ex);
            }
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            final Claims claims = JWTHelper.decodeJWT(jwt, 1);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (claims != null && authentication == null) {
                final Long userId = (Long) claims.get("user_id");
                final Long exp = (Long) claims.get("exp");

                Optional<User> user = userService.getUserById(userId);

                if (exp > Instant.now().toEpochMilli()) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            user,
                            null
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            handlerExceptionResolver.resolveException(request, response, authHeader, ex);
        }
    }
}
