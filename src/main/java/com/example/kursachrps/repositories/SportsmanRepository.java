package com.example.kursachrps.repositories;

import com.example.kursachrps.models.Sportsman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SportsmanRepository extends JpaRepository<Sportsman, String> {

    Optional<Sportsman> findById(String id);

    Optional<Sportsman> findByEmail(String email);

    Optional<List<Sportsman>> findByIsRegionalTeamSportsman(Boolean isRegionalTeamSportsman);
}
