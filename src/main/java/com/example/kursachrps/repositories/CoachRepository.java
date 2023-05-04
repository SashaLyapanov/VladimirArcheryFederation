package com.example.kursachrps.repositories;

import com.example.kursachrps.Models.User;

import java.util.Optional;

public interface CoachRepository extends UserRepository {
    Optional<User> findByEmail(String email);
}
