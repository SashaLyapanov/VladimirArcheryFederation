package com.example.kursachrps.service;

import com.example.kursachrps.models.AboutFederation;
import com.example.kursachrps.repositories.AboutFederationRepository;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AboutFederationService {

    private final RestTemplate restTemplate;
    AboutFederationRepository aboutFederationRepository;

    @Value("${file.manager.path}")
    private String fileManagerPath;

    AboutFederationService(AboutFederationRepository aboutFederationRepository, RestTemplate restTemplate) {
        this.aboutFederationRepository = aboutFederationRepository;
        this.restTemplate = restTemplate;
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

    public boolean editFilesAboutFederation(String id, MultipartFile[] files) {
        AboutFederation aboutFederation = aboutFederationRepository.findById(id).orElse(null);
        if (aboutFederation != null) {
            try {
                MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

                if (files != null && files.length > 0) {
                    for (MultipartFile file: files) {
                        body.add("files", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));
                    }
                }

                org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);

                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity(body, headers);

                ResponseEntity<?> responseFromFileManager = restTemplate.exchange(
                        fileManagerPath + "/aboutFederation/uploadFiles",
                        HttpMethod.POST,
                        requestEntity,
                        String.class
                );
                if (responseFromFileManager.getStatusCode().is2xxSuccessful()) {
                    // Обработка успешного ответа
                    if (files != null && files.length > 0) {
                        List<String> fileNames = Arrays.stream(files)
                                .map(MultipartFile::getOriginalFilename)
                                .collect(Collectors.toList());
                        aboutFederation.setListFileNames(String.join(", ", fileNames));
                    } else {
                        aboutFederation.setListFileNames(null);
                    }
                    aboutFederationRepository.save(aboutFederation);
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (java.io.IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return false;
        }
        return false;
    }
}
