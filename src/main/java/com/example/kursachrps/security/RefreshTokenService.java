package com.example.kursachrps.security;

import com.example.kursachrps.models.RefreshToken;
import com.example.kursachrps.models.User;
import com.example.kursachrps.repositories.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = refreshTokenRepository.findByUser(user).orElse(null);
        if (refreshToken == null) {
            refreshToken = new RefreshToken();
            refreshToken.setUser(user);
        }
        refreshToken.setToken(jwtUtils.generateRefreshToken(user));
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(10));
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> getRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public Optional<RefreshToken> getRefreshTokenByUserId(String userId) {
        return refreshTokenRepository.findByUserId(userId);
    }

    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }

}
