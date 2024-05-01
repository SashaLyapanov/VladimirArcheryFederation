package com.example.kursachrps.service;

import com.example.kursachrps.models.BowType;
import com.example.kursachrps.models.Competition;
import com.example.kursachrps.models.ProtocolStage8;
import com.example.kursachrps.repositories.CompetitionRepository;
import com.example.kursachrps.repositories.ProtocolStage8Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class ProtocolStage8Service {
    private static final String MAN_ID = "c99ccd51-5731-42a3-9cfc-31cc4011e035";
    private static final String WOMAN_ID = "848f4054-a9c1-4525-9e10-2ab07e3e9b4c";

    private final ProtocolStage8Repository protocolStage8Repository;
    private final CompetitionRepository competitionRepository;

    @Autowired
    public ProtocolStage8Service(ProtocolStage8Repository protocolStage8Repository, CompetitionRepository competitionRepository) {

        this.protocolStage8Repository = protocolStage8Repository;
        this.competitionRepository = competitionRepository;
    }

    public List<ProtocolStage8> calculateSportsmanPlaceInStage8(List<ProtocolStage8> protocolStage8List, String competitionId) {
        Competition competition = competitionRepository.findById(competitionId).orElse(null);
        if (competition != null) {
            List<BowType> bowTypeList = new ArrayList<>();
            bowTypeList.addAll(competition.getBowTypeList());
            List<ProtocolStage8> finalProtocolStage8List = new ArrayList<>();
            for (BowType bowType: bowTypeList) {
                List<ProtocolStage8> listForSomeBowTypeMAN = new ArrayList<>();
                List<ProtocolStage8> listForSomeBowTypeWOMAN = new ArrayList<>();
                for (ProtocolStage8 protocolStage8: protocolStage8List) {
                    if (protocolStage8.getBowType() == bowType && Objects.equals(protocolStage8.getSportsman().getSex().getId(), MAN_ID)) {
                        listForSomeBowTypeMAN.add(protocolStage8);
                    } else if (protocolStage8.getBowType() == bowType && Objects.equals(protocolStage8.getSportsman().getSex().getId(), WOMAN_ID)) {
                        listForSomeBowTypeWOMAN.add(protocolStage8);
                    }
                }
                Collections.sort(listForSomeBowTypeMAN);
                Collections.sort(listForSomeBowTypeWOMAN);
                int placeMan = 1;
                int placeWoman = 1;
                for (ProtocolStage8 protocolStage8: listForSomeBowTypeMAN) {
                    protocolStage8.setPlace(placeMan);
                    finalProtocolStage8List.add(protocolStage8);
                    placeMan++;
                }
                for (ProtocolStage8 protocolStage8: listForSomeBowTypeWOMAN) {
                    protocolStage8.setPlace(placeWoman);
                    finalProtocolStage8List.add(protocolStage8);
                    placeWoman++;
                }
            }
            return finalProtocolStage8List;
        }
        return null;
    }

    public List<ProtocolStage8> findLeaders(String competitionId, BowType bowType, String sexId) {
        List<ProtocolStage8> sportsmanList = protocolStage8Repository.findProtocolStage8ByCompetitionIdAndBowTypeIdAndSportsmanSexId(competitionId, bowType.getId(), sexId);
        Collections.sort(sportsmanList);
        return sportsmanList;
    }
}
