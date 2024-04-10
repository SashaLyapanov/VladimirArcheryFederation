package com.example.kursachrps.service;

import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.models.Sportsman;
import com.example.kursachrps.repositories.SportsmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
public class PersonalAccountSportsmanService {

    private final PasswordEncoder passwordEncoder;
    private final SportsmanRepository sportsmanRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public PersonalAccountSportsmanService(PasswordEncoder passwordEncoder, SportsmanRepository sportsmanRepository, RestTemplate restTemplate) {
        this.passwordEncoder = passwordEncoder;
        this.sportsmanRepository = sportsmanRepository;
        this.restTemplate = restTemplate;
    }

    /**
     * Хеширование пароля
     */
    @Transactional
    public SportsmanDTO hashPassword(SportsmanDTO sportsmanDTO) {
        sportsmanDTO.setPassword(passwordEncoder.encode(sportsmanDTO.getPassword()));
        return sportsmanDTO;
    }

    /**
     * Метод для редактирования собственного профиля у спортсмена
     */
    @Transactional
    public Sportsman editProfile(String id, Sportsman updatedSportsman) {

        Sportsman sportsman = sportsmanRepository.findById(id).orElse(null);

        assert sportsman != null;
        if (updatedSportsman.getPassword() != null) {
            sportsman.setPassword(updatedSportsman.getPassword());
        }
        sportsman.setFirstName(updatedSportsman.getFirstName());
        sportsman.setSurname(updatedSportsman.getSurname());
        sportsman.setPatronymic(updatedSportsman.getPatronymic());
        sportsman.setBirthDate(updatedSportsman.getBirthDate());
        sportsman.setSportsTitle(updatedSportsman.getSportsTitle());
        sportsman.setSex(updatedSportsman.getSex());
        sportsman.setClub(updatedSportsman.getClub());

        return sportsman;
    }

    /**
     * Метод для редактирования аватарки в личном кабинете спортсмена
     */
    @Transactional
    public void uploadAvatarImage(String sportsmanId, MultipartFile file) throws IOException {
        Sportsman sportsman = sportsmanRepository.findById(sportsmanId).orElse(null);
        if (sportsman != null) {
            String oldFileName = sportsman.getAvatarImage();
            String newFileName = sportsmanId + "_" + file.getOriginalFilename();
            sportsman.setAvatarImage(newFileName);
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("oldFileName", oldFileName);
            body.add("sportsmanId", sportsmanId);
            body.add("file", new FileSystemResource(Objects.requireNonNull(convertMultipartFileToFile(file))));

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.exchange("http://localhost:8081/personalAccount/upload", HttpMethod.POST, requestEntity, String.class);

            System.out.println(response);
        }
    }

    private File convertMultipartFileToFile(MultipartFile file) {
        if (!file.isEmpty()) {
            File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
            try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
                fos.write(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return convertedFile;
        }
        else {
            return null;
        }
    }

    public Sportsman getSportsmanById(String sportsmanId) {
        return sportsmanRepository.findById(sportsmanId).orElse(null);
    }
}
