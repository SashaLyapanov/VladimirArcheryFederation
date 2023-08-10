package com.example.kursachrps.repositories.RegistrAndAuth;

import com.example.kursachrps.models.User;

import java.util.Optional;

public interface CoachRepository extends UserRepository {
    Optional<User> findByEmail(String email);
}
