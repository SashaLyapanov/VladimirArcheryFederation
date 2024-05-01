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
            for (BowType bowType : bowTypeList) {
                List<ProtocolStage2> listForSomeBowTypeMAN = new ArrayList<>();
                List<ProtocolStage2> listForSomeBowTypeWOMAN = new ArrayList<>();
                for (ProtocolStage2 protocolStage2 : protocolStage2List) {
                    if (protocolStage2.getBowType() == bowType && Objects.equals(protocolStage2.getSportsman().getSex().getId(), MAN_ID)) {
                        listForSomeBowTypeMAN.add(protocolStage2);
                    } else if (protocolStage2.getBowType() == bowType && Objects.equals(protocolStage2.getSportsman().getSex().getId(), WOMAN_ID)) {
                        listForSomeBowTypeWOMAN.add(protocolStage2);
                    }
                }
                if (listForSomeBowTypeMAN.size() != 0) {
                    ArrayList<ProtocolStage2> couple14MAN = new ArrayList<>();
                    ArrayList<ProtocolStage2> couple23MAN = new ArrayList<>();
                    couple14MAN.add(listForSomeBowTypeMAN.get(0));
                    couple14MAN.add(listForSomeBowTypeMAN.get(3));
                    couple23MAN.add(listForSomeBowTypeMAN.get(1));
                    couple23MAN.add(listForSomeBowTypeMAN.get(2));

                    Collections.sort(couple14MAN);
                    Collections.sort(couple23MAN);

                    for (int i = 1; i <= couple14MAN.size(); i++) {
                        couple14MAN.get(i - 1).setPlace(i);
                        finalProtocolStage2List.add(couple14MAN.get(i - 1));
                    }
                    for (int i = 1; i <= couple23MAN.size(); i++) {
                        couple23MAN.get(i - 1).setPlace(i);
                        finalProtocolStage2List.add(couple23MAN.get(i - 1));
                    }
                }

                if (listForSomeBowTypeWOMAN.size() != 0) {
                    ArrayList<ProtocolStage2> couple14WOMAN = new ArrayList<>();
                    ArrayList<ProtocolStage2> couple23WOMAN = new ArrayList<>();
                    couple14WOMAN.add(listForSomeBowTypeWOMAN.get(0));
                    couple14WOMAN.add(listForSomeBowTypeWOMAN.get(3));
                    couple23WOMAN.add(listForSomeBowTypeWOMAN.get(1));
                    couple23WOMAN.add(listForSomeBowTypeWOMAN.get(2));

                    Collections.sort(couple14WOMAN);
                    Collections.sort(couple23WOMAN);


                    for (int i = 1; i <= couple14WOMAN.size(); i++) {
                        couple14WOMAN.get(i - 1).setPlace(i);
                        finalProtocolStage2List.add(couple14WOMAN.get(i - 1));
                    }
                    for (int i = 1; i <= couple23WOMAN.size(); i++) {
                        couple23WOMAN.get(i - 1).setPlace(i);
                        finalProtocolStage2List.add(couple23WOMAN.get(i - 1));
                    }
                }
            }
            return finalProtocolStage2List;
        }
        return null;
    }

    public List<ProtocolStage2> findLeaders(String competitionId, BowType bowType, String sexId) {
        List<ProtocolStage2> sportsmanList1 = protocolStage2Repository.findProtocolStage2ByCompetitionIdAndBowTypeIdAndSportsmanSexIdAndPlace(competitionId, bowType.getId(), sexId, 1);
        List<ProtocolStage2> sportsmanList2 = protocolStage2Repository.findProtocolStage2ByCompetitionIdAndBowTypeIdAndSportsmanSexIdAndPlace(competitionId, bowType.getId(), sexId, 2);
        List<ProtocolStage2> finalSportsmanList = new ArrayList<>();
        finalSportsmanList.addAll(sportsmanList1);
        finalSportsmanList.addAll(sportsmanList2);
        return finalSportsmanList;
    }
}
