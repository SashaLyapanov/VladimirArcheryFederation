package com.example.kursachrps.repositories;

import com.example.kursachrps.models.ProtocolStage2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProtocolStage2Repository extends JpaRepository<ProtocolStage2, String> {
}
