package com.example.kursachrps.repositories;

import com.example.kursachrps.models.Coach;
import com.example.kursachrps.models.Sportsman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoachMainRepository extends JpaRepository<Coach, Integer> {

    Optional<Coach> findById(String id);

    Optional<Coach> findByEmail(String email);

    Optional<Coach> findBySportsmen(Sportsman sportsman);

}
