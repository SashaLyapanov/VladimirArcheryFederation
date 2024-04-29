package com.example.kursachrps.repositories;

import com.example.kursachrps.models.ProtocolFinal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProtocolFinalRepository extends JpaRepository<ProtocolFinal, String> {
}
