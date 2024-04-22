package com.example.kursachrps.service;

import com.example.kursachrps.models.QualificationRound;
import com.example.kursachrps.repositories.QualificationRoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class QualificationRoundService {
    private static final String MAN_ID = "c99ccd51-5731-42a3-9cfc-31cc4011e035";
    private static final String WOMAN_ID = "848f4054-a9c1-4525-9e10-2ab07e3e9b4c";

    QualificationRoundRepository qualificationRoundRepository;

    @Autowired
    public QualificationRoundService(QualificationRoundRepository qualificationRoundRepository) {
        this.qualificationRoundRepository = qualificationRoundRepository;
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
}
