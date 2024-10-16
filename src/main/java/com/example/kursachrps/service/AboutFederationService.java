package com.example.kursachrps.service;

import com.example.kursachrps.models.AboutFederation;
import com.example.kursachrps.repositories.AboutFederationRepository;
import org.springframework.stereotype.Service;

@Service
public class AboutFederationService {

    AboutFederationRepository aboutFederationRepository;

    AboutFederationService(AboutFederationRepository aboutFederationRepository) {
        this.aboutFederationRepository = aboutFederationRepository;
    }

    public AboutFederation getAllAboutFederation() {
        return aboutFederationRepository.findAll().get(0);
    }

    public boolean editAboutFederation(String aboutFederationId, AboutFederation aboutFederation) {
        AboutFederation aboutFederationFromDB = aboutFederationRepository.findById(aboutFederationId).orElse(null);

        if (aboutFederation != null && aboutFederationFromDB != null) {
            aboutFederationFromDB.setManagers(aboutFederation.getManagers());
            aboutFederationFromDB.setContacts(aboutFederation.getContacts());
            aboutFederationFromDB.setListLinks(aboutFederation.getListLinks());
        }
        aboutFederationRepository.save(aboutFederationFromDB);
        return true;
    }

}
