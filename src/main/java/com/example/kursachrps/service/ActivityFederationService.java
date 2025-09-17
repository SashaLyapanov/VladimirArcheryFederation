package com.example.kursachrps.service;

import com.example.kursachrps.models.AboutFederation;
import com.example.kursachrps.models.ActivityFederation;
import com.example.kursachrps.repositories.ActivityFederationRepository;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ActivityFederationService {

    private final ActivityFederationRepository activityFederationRepository;
    private final RestTemplate restTemplate;

    @Value("${file.manager.path}")
    private String fileManagerPath;

    @Autowired
    public ActivityFederationService(ActivityFederationRepository activityFederationRepository, RestTemplate restTemplate) {
        this.activityFederationRepository = activityFederationRepository;
        this.restTemplate = restTemplate;
    }

    public Map<String, List<String>> getAllRegionalActivityFiles() {
        ActivityFederation activityFederation = activityFederationRepository.findById("7fa1257a-152j-258d-bca6-ba78fa263e0f").orElse(null);
        Map<String, List<String>> response = new HashMap<String, List<String>>();

        List<String> response3D = new ArrayList<>();
        if (activityFederation != null && activityFederation.getFileNamesThreeD() != null) {
            response3D.addAll(mapStringToList(activityFederation.getFileNamesThreeD()));
        }

        List<String> responseClassic = new ArrayList<>();
        if (activityFederation != null && activityFederation.getFileNamesClassic() != null) {
            responseClassic.addAll(mapStringToList(activityFederation.getFileNamesClassic()));
        }

        List<String> responseBiathlon = new ArrayList<>();
        if (activityFederation != null && activityFederation.getFileNamesBiathlon() != null) {
            responseBiathlon.addAll(mapStringToList(activityFederation.getFileNamesBiathlon()));
        }

        List<String> responseGeneral = new ArrayList<>();
        if (activityFederation != null && activityFederation.getFileNamesGeneral() != null) {
            responseGeneral.addAll(mapStringToList(activityFederation.getFileNamesGeneral()));
        }

        response.put("threeD", response3D);
        response.put("classic", responseClassic);
        response.put("biathlon", responseBiathlon);
        response.put("general", responseGeneral);

        return response;
    }

    List<String> mapStringToList(String str) {
        if (str != null) {
            String[] items = str.split(", ");
            return Arrays.asList(items);
        } else {
            return null;
        }
    }

    public boolean editFilesActivityFederation(String id, String flag, MultipartFile[] files) {
        ActivityFederation activityFederation = activityFederationRepository.findById(id).orElse(null);
        if (activityFederation != null) {
            try {
                MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

                switch (flag) {
                    case "General" -> body.add("flag", "General");
                    case "3D" -> body.add("flag", "3D");
                    case "Classic" -> body.add("flag", "Classic");
                    case "Biathlon" -> body.add("flag", "Biathlon");
                }

                if (files != null) {
                    for (MultipartFile file : files) {
                        body.add("files", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));
                    }
                }

                org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);

                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

                ResponseEntity<?> responseFromFileManager = restTemplate.exchange(
                        fileManagerPath + "/activityFederation/uploadFiles",
                        HttpMethod.POST,
                        requestEntity,
                        String.class
                );
                if (responseFromFileManager.getStatusCode().is2xxSuccessful()) {
                    // Обработка успешного ответа
                    if (flag.equals("General")) {
                        if (files != null && files.length > 0) {
                            List<String> fileNames = Arrays.stream(files)
                                    .map(MultipartFile::getOriginalFilename)
                                    .collect(Collectors.toList());
                            activityFederation.setFileNamesGeneral(String.join(", ", fileNames));
                        } else {
                            activityFederation.setFileNamesGeneral(null);
                        }
                    } else if (flag.equals("3D")) {
                        if (files != null && files.length > 0) {
                            List<String> fileNames = Arrays.stream(files)
                                    .map(MultipartFile::getOriginalFilename)
                                    .collect(Collectors.toList());
                            activityFederation.setFileNamesThreeD(String.join(", ", fileNames));
                        } else {
                            activityFederation.setFileNamesThreeD(null);
                        }
                    } else if (flag.equals("Classic")) {
                        if (files != null && files.length > 0) {
                            List<String> fileNames = Arrays.stream(files)
                                    .map(MultipartFile::getOriginalFilename)
                                    .collect(Collectors.toList());
                            activityFederation.setFileNamesClassic(String.join(", ", fileNames));
                        } else {
                            activityFederation.setFileNamesClassic(null);
                        }
                    } else if (flag.equals("Biathlon")) {
                        if (files != null && files.length > 0) {
                            List<String> fileNames = Arrays.stream(files)
                                    .map(MultipartFile::getOriginalFilename)
                                    .collect(Collectors.toList());
                            activityFederation.setFileNamesBiathlon(String.join(", ", fileNames));
                        } else {
                            activityFederation.setFileNamesBiathlon(null);
                        }
                    }
                    activityFederationRepository.save(activityFederation);
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
