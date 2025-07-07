package com.example.kursachrps.security;

import com.example.kursachrps.models.User;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {


    private final long accessTokenValidity = 600000; //10 минут
    private final long refreshTokenValidity = 10L * 24 * 60 * 60 * 1000; //10 дней

    @Value("${jwt.secret.access}")
    private String accessSecret;

    @Value("${jwt.secret.refresh}")
    private String refreshSecret;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(accessSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private SecretKey getRefreshSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(refreshSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .subject(user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenValidity))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .subject(user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenValidity))
                .signWith(getRefreshSigningKey())
                .compact();
    }

    public String extractUserId(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(getSigningKey())
                .build();
        return parser.parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String extractUserIdForRefreshToken(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(getRefreshSigningKey())
                .build();
        return parser.parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token, String id) {
        String userId = extractUserId(token);
        return userId.equals(id) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(getSigningKey())
                .build();
        return parser.parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }
}
