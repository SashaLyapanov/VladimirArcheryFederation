package com.example.kursachrps.repositories;

import com.example.kursachrps.models.Sportsman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SportsmanMainRepository extends JpaRepository<Sportsman, Integer> {

    Optional<Sportsman> findById(String id);

    Optional<Sportsman> findByEmail(String email);
}
