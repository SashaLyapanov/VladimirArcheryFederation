package com.example.kursachrps.service;

import com.example.kursachrps.models.*;
import com.example.kursachrps.repositories.BowTypeRepository;
import com.example.kursachrps.repositories.CompetitionRepository;
import com.example.kursachrps.repositories.SexRepository;
import com.example.kursachrps.repositories.SportsmanRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Service
public class ParserExcelData {
    private static final String LongBow_3D = "af44dbd5-21bb-41f1-b732-af5706b8153d";
    private static final String CompoundBow_3D = "62cb799b-0ff8-4843-82c0-61a215d4af97";
    private static final String CL_3D = "351c3a7e-64b4-4749-b8c8-bb1ecb2f3ef2";
    private static final String BL_3D = "ac6a3094-b354-4ebd-8bb1-19111742c764";
    private static final String Sporting = "62cb799b-0ff8-4843-b732-af5706b8153d";

    private static final String MAN = "c99ccd51-5731-42a3-9cfc-31cc4011e035";
    private static final String WOMAN = "848f4054-a9c1-4525-9e10-2ab07e3e9b4c";


    private BowTypeRepository bowTypeRepository;
    private CompetitionRepository competitionRepository;
    private SportsmanRepository sportsmanRepository;
    private SexRepository sexRepository;
    private SportsTitleService sportsTitleService;

    @Autowired
    public ParserExcelData(BowTypeRepository bowTypeRepository,
                           CompetitionRepository competitionRepository,
                           SportsmanRepository sportsmanRepository, SexRepository sexRepository, SportsTitleService sportsTitleService) {
        this.bowTypeRepository = bowTypeRepository;
        this.competitionRepository = competitionRepository;
        this.sportsmanRepository = sportsmanRepository;
        this.sexRepository = sexRepository;
        this.sportsTitleService = sportsTitleService;
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

    public Sex findSexByName(Cell qualificationData) {
        return sexRepository.findSexByName(qualificationData.toString());
    }

    public SportsTitle findSportsTitleBySumInQualification(Sex sex, BowType bowType, int sum) {
        SportsTitle sportsTitle = new SportsTitle();
        if (Objects.equals(bowType.getId(), LongBow_3D)) {
            if (Objects.equals(sex.getId(), MAN)) {
                sportsTitle = sportsTitleService.initializeSportTitleForLongBow_3D_MAN(sum);
            } else {
                sportsTitle = sportsTitleService.initializeSportTitleForLongBow_3D_WOMAN(sum);
            }
        } else if (Objects.equals(bowType.getId(), CompoundBow_3D)) {
            if (Objects.equals(sex.getId(), MAN)) {
                sportsTitle = sportsTitleService.initializeSportTitleForCompoundBow_3D_MAN(sum);
            } else {
                sportsTitle = sportsTitleService.initializeSportTitleForCompoundBow_3D_WOMAN(sum);
            }
        } else if (Objects.equals(bowType.getId(), CL_3D)) {
            if (Objects.equals(sex.getId(), MAN)) {
                sportsTitle = sportsTitleService.initializeSportTitleForCL_3D_MAN(sum);
            } else {
                sportsTitle = sportsTitleService.initializeSportTitleForCL_3D_WOMAN(sum);
            }
        } else if (Objects.equals(bowType.getId(), BL_3D)) {
            if (Objects.equals(sex.getId(), MAN)) {
                sportsTitle = sportsTitleService.initializeSportTitleForBL_3D_MAN(sum);
            } else {
                sportsTitle = sportsTitleService.initializeSportTitleForBL_3D_WOMAN(sum);
            }
        } else if (Objects.equals(bowType.getId(), Sporting)) {
            if (Objects.equals(sex.getId(), MAN)) {
                sportsTitle = sportsTitleService.initializeSportTitleForSporting_3D_MAN(sum);
            } else {
                sportsTitle = sportsTitleService.initializeSportTitleForSporting_3D_WOMAN(sum);
            }
        }

        return sportsTitle;
    }
}
