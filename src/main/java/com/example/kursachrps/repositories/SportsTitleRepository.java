package com.example.kursachrps.repositories;

import com.example.kursachrps.Models.SportsTitle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SportsTitleRepository extends JpaRepository<SportsTitle, Integer> {

}
