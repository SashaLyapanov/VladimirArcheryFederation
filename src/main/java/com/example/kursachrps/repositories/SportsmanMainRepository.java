package com.example.kursachrps.repositories;

import com.example.kursachrps.models.Sportsman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SportsmanMainRepository extends JpaRepository<Sportsman, Integer> {

    Optional<Sportsman> findById(int id);

    Optional<Sportsman> findByEmail(String email);

    @Query("SELECT s FROM Sportsman s WHERE s.personalCoach.id = :coachId")
    List<Sportsman> findAllSportsmanByPersonalCoach(@Param("coachId") int coachId);
}
