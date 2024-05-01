package com.example.kursachrps.service;

import com.example.kursachrps.models.BowType;
import com.example.kursachrps.models.Competition;
import com.example.kursachrps.models.ProtocolStage4;
import com.example.kursachrps.repositories.CompetitionRepository;
import com.example.kursachrps.repositories.ProtocolStage4Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class ProtocolStage4Service {
    private static final String MAN_ID = "c99ccd51-5731-42a3-9cfc-31cc4011e035";
    private static final String WOMAN_ID = "848f4054-a9c1-4525-9e10-2ab07e3e9b4c";

    private final ProtocolStage4Repository protocolStage4Repository;
    private final CompetitionRepository competitionRepository;

    @Autowired
    public ProtocolStage4Service(ProtocolStage4Repository protocolStage4Repository, CompetitionRepository competitionRepository) {

        this.protocolStage4Repository = protocolStage4Repository;
        this.competitionRepository = competitionRepository;
    }

    public List<ProtocolStage4> calculateSportsmanPlaceInStage4(List<ProtocolStage4> protocolStage4List, String competitionId) {
        Competition competition = competitionRepository.findById(competitionId).orElse(null);
        if (competition != null) {
            List<BowType> bowTypeList = new ArrayList<>();
            bowTypeList.addAll(competition.getBowTypeList());
            List<ProtocolStage4> finalProtocolStage4List = new ArrayList<>();
            for (BowType bowType: bowTypeList) {
                List<ProtocolStage4> listForSomeBowTypeMAN = new ArrayList<>();
                List<ProtocolStage4> listForSomeBowTypeWOMAN = new ArrayList<>();
                for (ProtocolStage4 protocolStage4: protocolStage4List) {
                    if (protocolStage4.getBowType() == bowType && Objects.equals(protocolStage4.getSportsman().getSex().getId(), MAN_ID)) {
                        listForSomeBowTypeMAN.add(protocolStage4);
                    } else if (protocolStage4.getBowType() == bowType && Objects.equals(protocolStage4.getSportsman().getSex().getId(), WOMAN_ID)) {
                        listForSomeBowTypeWOMAN.add(protocolStage4);
                    }
                }
                Collections.sort(listForSomeBowTypeMAN);
                Collections.sort(listForSomeBowTypeWOMAN);
                int placeMan = 1;
                int placeWoman = 1;
                for (ProtocolStage4 protocolStage4: listForSomeBowTypeMAN) {
                    protocolStage4.setPlace(placeMan);
                    finalProtocolStage4List.add(protocolStage4);
                    placeMan++;
                }
                for (ProtocolStage4 protocolStage4: listForSomeBowTypeWOMAN) {
                    protocolStage4.setPlace(placeWoman);
                    finalProtocolStage4List.add(protocolStage4);
                    placeWoman++;
                }
            }
            return finalProtocolStage4List;
        }
        return null;
    }

    public List<ProtocolStage4> findLeaders(String competitionId, BowType bowType, String sexId) {
        List<ProtocolStage4> sportsmanList = protocolStage4Repository.findProtocolStage4ByCompetitionIdAndBowTypeIdAndSportsmanSexId(competitionId, bowType.getId(), sexId);
        Collections.sort(sportsmanList);
        return sportsmanList;
    }
}
