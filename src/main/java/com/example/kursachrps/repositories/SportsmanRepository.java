package com.example.kursachrps.repositories;

import com.example.kursachrps.models.Competition;
import com.example.kursachrps.models.Sportsman;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface SportsmanRepository extends JpaRepository<Sportsman, String>, JpaSpecificationExecutor<Sportsman> {

    Optional<Sportsman> findById(String id);

    Optional<Sportsman> findByEmail(String email);

    Optional<List<Sportsman>> findByIsRegionalTeamSportsman(Boolean isRegionalTeamSportsman);

    Optional<Sportsman> findSportsmanByFirstNameAndSurnameAndPatronymicAndBirthDate(String firstName, String surname, String patronymic, Date birthDate);

    Optional<Sportsman> findSportsmanByFirstNameAndSurnameAndPatronymic(String firstName, String surname, String patronymic);

}
