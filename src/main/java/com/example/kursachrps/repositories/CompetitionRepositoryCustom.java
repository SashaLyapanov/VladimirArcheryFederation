package com.example.kursachrps.repositories;

import com.example.kursachrps.models.Competition;
import com.example.kursachrps.models.CompetitionType;

import java.util.Date;
import java.util.List;

public interface CompetitionRepositoryCustom {
    List<Competition> findCompetitionByParams(String name, Date date, CompetitionType type);
}
