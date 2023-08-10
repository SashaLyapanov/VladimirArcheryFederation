package com.example.kursachrps.repositories;

import com.example.kursachrps.models.SessionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionTypeRepository extends JpaRepository<SessionType, Integer> {

}
