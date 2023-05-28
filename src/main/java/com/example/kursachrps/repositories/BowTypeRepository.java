package com.example.kursachrps.repositories;

import com.example.kursachrps.Models.BowType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BowTypeRepository extends JpaRepository<BowType, Integer> {

}
