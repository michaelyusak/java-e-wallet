package com.java_e_wallet.e_wallet_service.helper;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;

import javax.crypto.SecretKey;

import com.java_e_wallet.e_wallet_service.config.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JWTHelper {
    public static String generateToken(Long userId, int purpose) {
        Config config = Config.getConfigInstance();

        Long now = Instant.now().toEpochMilli();
        
        Long exp;
        String key;

        switch (purpose) {
            case 1:
                exp = now + config.getAccessTokenTTL() * 60000;
                key = config.getTokenSecretKey();
                break;
            
            case 2:
                exp = now + config.getRefreshTokenTTL() * 60000;
                key = config.getTokenSecretKey();
                break;

            default:
                exp = now + config.getAccessTokenTTL() * 60000;
                key = config.getTokenSecretKey();
                break;
        }

        Key signingKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
            .claim("user_id", userId)
            .claim("iat", now)
            .claim("exp", exp)
            .claim("iss", config.getTokenIssuer())
            .signWith(signingKey)
            .compact();
    }

    public static Claims decodeJWT(String jwt, int purpose) {
        Config config = Config.getConfigInstance();

        String key;

        switch (purpose) {
            case 1:
                key = config.getTokenSecretKey();
                break;
            
            case 2:
                key = config.getTokenSecretKey();
                break;

            default:
                key = config.getTokenSecretKey();
                break;
        }

        Key decodingKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));

        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) decodingKey)
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
        return claims;
    }
}
