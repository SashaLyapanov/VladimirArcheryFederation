package com.example.kursachrps.repositories;

import com.example.kursachrps.models.Protocol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProtocolRepository extends JpaRepository<Protocol, String> {

    Protocol findProtocolByCompetitionId(String competitionId);
}
