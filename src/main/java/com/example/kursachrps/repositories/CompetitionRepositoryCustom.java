package com.example.kursachrps.repositories;

import com.example.kursachrps.models.Competition;
import com.example.kursachrps.models.CompetitionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface CompetitionRepositoryCustom {
    Page<Competition> findCompetitionByParams(String name, Date date, CompetitionType type, Pageable pageable);
}
