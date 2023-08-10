package com.example.kursachrps.repositories;

import com.example.kursachrps.models.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoachMainRepository extends JpaRepository<Coach, Integer> {

    Optional<Coach> findByEmail(String email);

}
