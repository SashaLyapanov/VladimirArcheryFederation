package com.example.kursachrps.repositories.RegistrAndAuth;

import com.example.kursachrps.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

/**
 * Репозиторий для наследования репозиториями спортсменов, судий, админов, тренеров.
 */

@NoRepositoryBean
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);
}
