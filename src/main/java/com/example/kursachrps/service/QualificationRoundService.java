package com.example.kursachrps.service;

import com.example.kursachrps.models.BowType;
import com.example.kursachrps.models.Competition;
import com.example.kursachrps.models.QualificationRound;
import com.example.kursachrps.repositories.CompetitionRepository;
import com.example.kursachrps.repositories.QualificationRoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class QualificationRoundService {
    private static final String MAN_ID = "c99ccd51-5731-42a3-9cfc-31cc4011e035";
    private static final String WOMAN_ID = "848f4054-a9c1-4525-9e10-2ab07e3e9b4c";

    QualificationRoundRepository qualificationRoundRepository;
    CompetitionRepository competitionRepository;

    @Autowired
    public QualificationRoundService(QualificationRoundRepository qualificationRoundRepository,
                                     CompetitionRepository competitionRepository) {
        this.qualificationRoundRepository = qualificationRoundRepository;
        this.competitionRepository = competitionRepository;
    }

    public List<QualificationRound> getSportsmanMANListInBowType(List<QualificationRound> sportsmanListInBowType) {
        List<QualificationRound> sportsmanMANListInBowType = new ArrayList<>();
        for (QualificationRound qualificationRound: sportsmanListInBowType) {
            if (Objects.equals(qualificationRound.getSportsman().getSex().getId(), MAN_ID)) {
                sportsmanMANListInBowType.add(qualificationRound);
            }
        }
        return sportsmanMANListInBowType;
    }

    public List<QualificationRound> getSportsmanWOMANListInBowType(List<QualificationRound> sportsmanListInBowType) {
        List<QualificationRound> sportsmanWOMANListInBowType = new ArrayList<>();
        for (QualificationRound qualificationRound: sportsmanListInBowType) {
            if (Objects.equals(qualificationRound.getSportsman().getSex().getId(), WOMAN_ID)) {
                sportsmanWOMANListInBowType.add(qualificationRound);
            }
        }
        return sportsmanWOMANListInBowType;
    }

    /**
     * Просчитывание мест, которые заняли спортсмены по итогам квалификации
     * Учитвыается тип лука и пол спортсмена
     */
    public List<QualificationRound> calculateSportsmanPlaceInQualification(List<QualificationRound> qualificationRoundList, String competitionId) {
        Competition competition = competitionRepository.findById(competitionId).orElse(null);
        if (competition != null) {
            List<BowType> bowTypeList = new ArrayList<>();
            bowTypeList.addAll(competition.getBowTypeList());
            List<QualificationRound> finalQualificationRoundList = new ArrayList<>();
            for (BowType bowType: bowTypeList) {
                List<QualificationRound> listForSomeBowTypeMAN = new ArrayList<>();
                List<QualificationRound> listForSomeBowTypeWOMAN = new ArrayList<>();
                for (QualificationRound qualificationRound: qualificationRoundList) {
                    if (qualificationRound.getBowType() == bowType && Objects.equals(qualificationRound.getSportsman().getSex().getId(), MAN_ID)) {
                        listForSomeBowTypeMAN.add(qualificationRound);
                    } else if (qualificationRound.getBowType() == bowType && Objects.equals(qualificationRound.getSportsman().getSex().getId(), WOMAN_ID)) {
                        listForSomeBowTypeWOMAN.add(qualificationRound);
                    }
                }
                Collections.sort(listForSomeBowTypeMAN);
                Collections.sort(listForSomeBowTypeWOMAN);
                int placeMan = 1;
                int placeWoman = 1;
                for (QualificationRound qualificationRound: listForSomeBowTypeMAN) {
                    qualificationRound.setPlace(placeMan);
                    finalQualificationRoundList.add(qualificationRound);
                    placeMan++;
                }
                for (QualificationRound qualificationRound: listForSomeBowTypeWOMAN) {
                    qualificationRound.setPlace(placeWoman);
                    finalQualificationRoundList.add(qualificationRound);
                    placeWoman++;
                }
            }
            return finalQualificationRoundList;
        }
        return null;
    }
}
