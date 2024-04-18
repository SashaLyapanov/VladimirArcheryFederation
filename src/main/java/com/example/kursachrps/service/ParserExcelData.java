package com.example.kursachrps.service;

import com.example.kursachrps.models.BowType;
import com.example.kursachrps.models.Competition;
import com.example.kursachrps.models.Sportsman;
import com.example.kursachrps.repositories.BowTypeRepository;
import com.example.kursachrps.repositories.CompetitionRepository;
import com.example.kursachrps.repositories.SportsmanRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ParserExcelData {
    private BowTypeRepository bowTypeRepository;
    private CompetitionRepository competitionRepository;
    private SportsmanRepository sportsmanRepository;

    @Autowired
    public ParserExcelData(BowTypeRepository bowTypeRepository,
                           CompetitionRepository competitionRepository,
                           SportsmanRepository sportsmanRepository) {
        this.bowTypeRepository = bowTypeRepository;
        this.competitionRepository = competitionRepository;
        this.sportsmanRepository = sportsmanRepository;
    }

    public BowType findBowTypeByBowTypeName(String bowTypeName) {
        return bowTypeRepository.findBowTypeByBowTypeName(bowTypeName).orElse(null);
    }

    public Competition findCompetitionById(String competitionId) {
        return competitionRepository.findById(competitionId).orElse(null);
    }

    public Sportsman findSportsmanByFioAndBirthDate(Cell qualificationData, Cell birthDate) {
        String[] FIO = qualificationData.toString().split(" ");
        String surname = FIO[0];
        String firstName = FIO[1];
        String patronymic = FIO[2];

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", new java.util.Locale("ru", "RU"));
        try {
            Date date = dateFormat.parse(birthDate.toString());
//            return sportsmanRepository.findSportsmanByFirstNameAndSurnameAndPatronymicAndBirthDate(firstName, surname, patronymic, date).orElse(null);
            return sportsmanRepository.findSportsmanByFirstNameAndSurnameAndPatronymic(firstName, surname, patronymic).orElse(null);
        } catch (Exception e) {
            System.out.println("Ошибка при преобразовании строки в дату: " + e.getMessage());
        }
        return null;
    }
}
