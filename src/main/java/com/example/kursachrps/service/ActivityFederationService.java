package com.example.kursachrps.service;

import com.example.kursachrps.models.ActivityFederation;
import com.example.kursachrps.repositories.ActivityFederationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityFederationService {

    private final ActivityFederationRepository activityFederationRepository;

    @Autowired
    public ActivityFederationService(ActivityFederationRepository activityFederationRepository) {
        this.activityFederationRepository = activityFederationRepository;
    }

    public List<String> getAllRegionalActivityFiles() {
        List<ActivityFederation> list =  activityFederationRepository.findAll();
        List<String> response = new ArrayList<>();
        for (ActivityFederation activity: list) {
            response.add(activity.getFileName());
        }
        return response;
    }
}
