package com.example.kursachrps.service;

import com.example.kursachrps.models.BowType;
import com.example.kursachrps.models.Competition;
import com.example.kursachrps.models.ProtocolFinal;
import com.example.kursachrps.repositories.CompetitionRepository;
import com.example.kursachrps.repositories.ProtocolFinalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class ProtocolFinalService {
    private static final String MAN_ID = "c99ccd51-5731-42a3-9cfc-31cc4011e035";
    private static final String WOMAN_ID = "848f4054-a9c1-4525-9e10-2ab07e3e9b4c";

    private final ProtocolFinalRepository protocolFinalRepository;
    private final CompetitionRepository competitionRepository;

    @Autowired
    public ProtocolFinalService(ProtocolFinalRepository protocolFinalRepository,
                                CompetitionRepository competitionRepository) {

        this.protocolFinalRepository = protocolFinalRepository;
        this.competitionRepository = competitionRepository;
    }

    public List<ProtocolFinal> calculateSportsmanPlaceInFinal(List<ProtocolFinal> protocolFinalList, String competitionId) {
        Competition competition = competitionRepository.findById(competitionId).orElse(null);
        if (competition != null) {
            List<BowType> bowTypeList = new ArrayList<>();
            bowTypeList.addAll(competition.getBowTypeList());
            List<ProtocolFinal> protocolFinals = new ArrayList<>();
            for (BowType bowType: bowTypeList) {
                List<ProtocolFinal> listForSomeBowTypeMAN = new ArrayList<>();
                List<ProtocolFinal> listForSomeBowTypeWOMAN = new ArrayList<>();
                for (ProtocolFinal protocolFinal: protocolFinalList) {
                    if (protocolFinal.getBowType() == bowType && Objects.equals(protocolFinal.getSportsman().getSex().getId(), MAN_ID)) {
                        listForSomeBowTypeMAN.add(protocolFinal);
                    } else if (protocolFinal.getBowType() == bowType && Objects.equals(protocolFinal.getSportsman().getSex().getId(), WOMAN_ID)) {
                        listForSomeBowTypeWOMAN.add(protocolFinal);
                    }
                }

                if (listForSomeBowTypeMAN.size() > 0) {
                    List<ProtocolFinal> finalListMan12 = new ArrayList<>();
                    List<ProtocolFinal> finalListMan34 = new ArrayList<>();
                    for (int i = 0; i < 2 ; i++) {
                        finalListMan12.add(listForSomeBowTypeMAN.get(i));
                    }
                    for (int i = 2; i < 4 ; i++) {
                        finalListMan34.add(listForSomeBowTypeMAN.get(i));
                    }
                    Collections.sort(finalListMan12);
                    Collections.sort(finalListMan34);
                    for (int i = 1; i <= 2; i++) {
                        finalListMan12.get(i-1).setPlace(i);
                    }
                    for (int i = 3; i <= 4; i++) {
                        finalListMan34.get(i-3).setPlace(i);
                    }
                    protocolFinals.addAll(finalListMan12);
                    protocolFinals.addAll(finalListMan34);
                }

                if (listForSomeBowTypeWOMAN.size() > 0) {
                    List<ProtocolFinal> finalListWoman12 = new ArrayList<>();
                    List<ProtocolFinal> finalListWoman34 = new ArrayList<>();
                    for (int i = 0; i < 2 ; i++) {
                        finalListWoman12.add(listForSomeBowTypeWOMAN.get(i));
                    }
                    for (int i = 2; i < 4 ; i++) {
                        finalListWoman34.add(listForSomeBowTypeWOMAN.get(i));
                    }
                    Collections.sort(finalListWoman12);
                    Collections.sort(finalListWoman34);
                    for (int i = 1; i <= 2; i++) {
                        finalListWoman12.get(i-1).setPlace(i);
                    }
                    for (int i = 3; i <= 4; i++) {
                        finalListWoman34.get(i-3).setPlace(i);
                    }
                    protocolFinals.addAll(finalListWoman12);
                    protocolFinals.addAll(finalListWoman34);
                }
            }
            return protocolFinals;
        }
        return null;
    }
}
