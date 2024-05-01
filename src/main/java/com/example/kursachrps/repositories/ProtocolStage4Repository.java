package com.example.kursachrps.repositories;

import com.example.kursachrps.models.ProtocolStage4;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProtocolStage4Repository extends JpaRepository<ProtocolStage4, String> {

    List<ProtocolStage4> findProtocolStage4ByCompetitionIdAndBowTypeIdAndSportsmanSexId(String competitionId, String bowTypeId, String sportsmanSexId);
}
