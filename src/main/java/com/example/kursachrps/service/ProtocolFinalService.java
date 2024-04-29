package com.example.kursachrps.service;

import com.example.kursachrps.models.ProtocolFinal;
import com.example.kursachrps.repositories.ProtocolFinalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProtocolFinalService {

    private final ProtocolFinalRepository protocolFinalRepository;

    @Autowired
    public ProtocolFinalService(ProtocolFinalRepository protocolFinalRepository) {

        this.protocolFinalRepository = protocolFinalRepository;
    }

    public List<ProtocolFinal> calculateSportsmanPlaceInFinal(List<ProtocolFinal> protocolFinalList, String competitionId) {
        return null;
    }
}
