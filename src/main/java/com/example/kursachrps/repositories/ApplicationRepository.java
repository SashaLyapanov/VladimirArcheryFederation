package com.example.kursachrps.repositories;

import com.example.kursachrps.models.Application;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, String> {

    @EntityGraph(attributePaths = {"sportsman"})
    List<Application> findApplicationByCompetitionId(String competitionId);

    List<Application> findApplicationByCompetitionIdAndBowTypeBowTypeName(String competitionId, String bowTypeName);

    List<Application> findApplicationBySportsmanId(String sportsmanId);

    Application findApplicationBySportsmanIdAndCompetitionId(String sportsmanId, String competitionId);

    Application findApplicationBySportsmanEmailAndCompetitionId(String sportsmanEmail, String competitionId);
}
