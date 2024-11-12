package com.java_e_wallet.e_wallet_service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.java_e_wallet.e_wallet_service.dto.ErrorResponseDTO;
import com.java_e_wallet.e_wallet_service.helper.JWTHelper;
import com.java_e_wallet.e_wallet_service.model.CustomUserDetails;
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
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String path = request.getRequestURI();
        final String authHeader = request.getHeader("Authorization");

        try {
            if (path.equals("/health") || path.startsWith("/auth/")) {
                filterChain.doFilter(request, response);
                return;
            }

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                ErrorResponseDTO res = new ErrorResponseDTO(HttpStatus.FORBIDDEN.value(), "need access token", "");

                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                hashMap.put("status_code", (int) res.getStatusCode());
                hashMap.put("message", (String) res.getMessage());

                JSONObject jsonObject = new JSONObject(hashMap);
                String jsonString = jsonObject.toJSONString();

                response.addHeader("Content-Type", "application/json");
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().write(jsonString);
                response.getWriter().flush();
                return;
            }

            final String jwt = authHeader.substring(7);
            final Claims claims = JWTHelper.decodeJWT(jwt, 1);

            if (claims != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                final Long userId = Long.valueOf((int) claims.get("user_id"));
                final Long exp = (Long) claims.get("exp");

                if (exp > Instant.now().toEpochMilli()) {
                    Optional<User> user = userService.getUserById(userId);

                    if (user.isPresent()) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                new CustomUserDetails(user.get()),
                                null,
                                null);

                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }

            filterChain.doFilter(request, response);

        } catch (Exception ex) {
            handlerExceptionResolver.resolveException(request, response, authHeader, ex);
        }
    }
}
