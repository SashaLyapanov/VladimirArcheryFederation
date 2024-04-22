package com.example.kursachrps.repositories;

import com.example.kursachrps.models.QualificationRound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QualificationRoundRepository extends JpaRepository<QualificationRound, String> {
    List<QualificationRound> findQualificationRoundByCompetitionIdAndBowTypeId(String competitionId, String bowTypeId);
}
