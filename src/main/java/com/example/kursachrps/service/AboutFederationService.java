package com.example.kursachrps.service;

import com.example.kursachrps.models.AboutFederation;
import com.example.kursachrps.repositories.AboutFederationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AboutFederationService {

    AboutFederationRepository aboutFederationRepository;

    AboutFederationService(AboutFederationRepository aboutFederationRepository) {
        this.aboutFederationRepository = aboutFederationRepository;
    }

    public AboutFederation getAllAboutFederation() {
        return aboutFederationRepository.findAll().get(0);
    }

    public AboutFederation editAboutFederation(String aboutFederationId, AboutFederation aboutFederation, MultipartFile file1, MultipartFile file2) throws IOException {
        AboutFederation aboutFederationFromDB = aboutFederationRepository.findById(aboutFederationId).orElse(null);

//        if (file1 != null && file1.getSize() != 0) {
//            aboutFederationFromDB.setRegulation(file1);
//        }
//        if (file2 != null && file2.getSize() != 0) {
//            aboutFederationFromDB.setHistory(file2);
//        }
        if (aboutFederation != null && aboutFederationFromDB != null) {
            aboutFederationFromDB.setManagers(aboutFederation.getManagers());
            aboutFederationFromDB.setContacts(aboutFederation.getContacts());

        }
        return aboutFederationFromDB;
    }

}
