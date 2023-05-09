package com.example.kursachrps.repositories.RegistrAndAuth;

import com.example.kursachrps.Models.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@Primary
public interface SportsmanRepository extends UserRepository {

    Optional<User> findByEmail(String email);

}
