package com.example.kursachrps.repositories;

import com.example.kursachrps.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Данный репозиторий создан для регистрации и аутентификации с авторизацией.
 * Также используется там, где надо явно работать с сущностью User, а не Sportsman, Admin, Judge
 */

@Repository
public interface UserMainRepository extends JpaRepository<User, String> {
    Optional<User> findById(String id);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndActivationCode(String email, String activationCode);

    boolean existsByEmail(String email);

    Optional<User> findByActivationCode(String code);
}
