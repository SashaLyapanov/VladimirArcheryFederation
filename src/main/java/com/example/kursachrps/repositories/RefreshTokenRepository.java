package com.example.kursachrps.repositories;

import com.example.kursachrps.models.RefreshToken;
import com.example.kursachrps.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);

    Optional<RefreshToken> findByUser(User user);

    Optional<RefreshToken> findByUserId(String userId);
}
