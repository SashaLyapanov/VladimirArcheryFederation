package com.example.kursachrps.repositories;

import com.example.kursachrps.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Данный репозиторий создан для регистрации и аутентификации с авторизацией.
 * Также используется там, где надо явно работать с сущностью User, а не Sportsman, Admin, Judge, Coach
 */

@Repository
public interface UserMainRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
