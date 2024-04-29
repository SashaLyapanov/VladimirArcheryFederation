package com.example.kursachrps.service;

import com.example.kursachrps.models.*;
import com.example.kursachrps.repositories.CompetitionRepository;
import com.example.kursachrps.repositories.ProtocolStage2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class ProtocolStage2Service {
    private static final String MAN_ID = "c99ccd51-5731-42a3-9cfc-31cc4011e035";
    private static final String WOMAN_ID = "848f4054-a9c1-4525-9e10-2ab07e3e9b4c";

    private final ProtocolStage2Repository protocolStage2Repository;
    private final CompetitionRepository competitionRepository;

    @Autowired
    public ProtocolStage2Service(ProtocolStage2Repository protocolStage2Repository,
                                 CompetitionRepository competitionRepository) {

        this.protocolStage2Repository = protocolStage2Repository;
        this.competitionRepository = competitionRepository;
    }

    public List<ProtocolStage2> calculateSportsmanPlaceInStage2(List<ProtocolStage2> protocolStage2List, String competitionId) {
        Competition competition = competitionRepository.findById(competitionId).orElse(null);
        if (competition != null) {
            List<BowType> bowTypeList = new ArrayList<>();
            bowTypeList.addAll(competition.getBowTypeList());
            List<ProtocolStage2> finalProtocolStage2List = new ArrayList<>();
            for (BowType bowType: bowTypeList) {
                List<ProtocolStage2> listForSomeBowTypeMAN = new ArrayList<>();
                List<ProtocolStage2> listForSomeBowTypeWOMAN = new ArrayList<>();
                for (ProtocolStage2 protocolStage2: protocolStage2List) {
                    if (protocolStage2.getBowType() == bowType && Objects.equals(protocolStage2.getSportsman().getSex().getId(), MAN_ID)) {
                        listForSomeBowTypeMAN.add(protocolStage2);
                    } else if (protocolStage2.getBowType() == bowType && Objects.equals(protocolStage2.getSportsman().getSex().getId(), WOMAN_ID)) {
                        listForSomeBowTypeWOMAN.add(protocolStage2);
                    }
                }
                Collections.sort(listForSomeBowTypeMAN);
                Collections.sort(listForSomeBowTypeWOMAN);
                int placeMan = 1;
                int placeWoman = 1;
                for (ProtocolStage2 protocolStage2: listForSomeBowTypeMAN) {
                    protocolStage2.setPlace(placeMan);
                    finalProtocolStage2List.add(protocolStage2);
                    placeMan++;
                }
                for (ProtocolStage2 protocolStage2: listForSomeBowTypeWOMAN) {
                    protocolStage2.setPlace(placeWoman);
                    finalProtocolStage2List.add(protocolStage2);
                    placeWoman++;
                }
            }
            return finalProtocolStage2List;
        }
        return null;
    }
}
