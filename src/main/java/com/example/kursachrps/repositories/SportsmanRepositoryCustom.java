package com.example.kursachrps.repositories;

import com.example.kursachrps.models.Sportsman;

import java.util.List;

public interface SportsmanRepositoryCustom {
    List<Sportsman> findSportsmenByParams(String surname, String name, String patronymic);
}
