package com.example.kursachrps.service;

import com.example.kursachrps.dto.CompetitionCreateDTO;
import com.example.kursachrps.mapper.CompetitionMapper;
import com.example.kursachrps.models.*;
import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.repositories.*;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private static final String LongBow_3D = "af44dbd5-21bb-41f1-b732-af5706b8153d";
    private static final String CompositeBow_3D = "62cb799b-0ff8-4843-82c0-61a215d4af97";
    private static final String CL_3D = "351c3a7e-64b4-4749-b8c8-bb1ecb2f3ef2";
    private static final String BL_3D = "ac6a3094-b354-4ebd-8bb1-19111742c764";
    private static final String Sporting = "62cb799b-0ff8-4843-b732-af5706b8153d";
    private static final String HistoryBow = "26454f95-0e38-45d4-a85e-dd37f4a04944";
    private static final String Olympic = "36671015-5c37-4e5c-8eed-9a353e927f32";
    private static final String Arbalet = "e6dc3841-98a3-4357-a139-61e48ac393e2";

    @Value("${file.manager.path}")
    private String fileManagerPath;

    private final RestTemplate restTemplate;
    private final SportsmanRepository sportsmanRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMainRepository userMainRepository;
    private final CompetitionRepository competitionRepository;
    private final ProtocolRepository protocolRepository;
    private final CompetitionMapper competitionMapper;
    private final SportsmanRepositoryImpl sportsmanRepositoryImpl;
    private final RegionalTeamRepository regionalTeamRepository;

    @Autowired
    public AdminService(RestTemplate restTemplate, SportsmanRepository sportsmanRepository,
                        PasswordEncoder passwordEncoder,
                        UserMainRepository userMainRepository,
                        CompetitionRepository competitionRepository,
                        ProtocolRepository protocolRepository, CompetitionMapper competitionMapper, SportsmanRepositoryImpl sportsmanRepositoryImpl, RegionalTeamRepository regionalTeamRepository) {
        this.restTemplate = restTemplate;
        this.sportsmanRepository = sportsmanRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMainRepository = userMainRepository;
        this.competitionRepository = competitionRepository;
        this.protocolRepository = protocolRepository;
        this.competitionMapper = competitionMapper;
        this.sportsmanRepositoryImpl = sportsmanRepositoryImpl;
        this.regionalTeamRepository = regionalTeamRepository;
    }


    /////////////////////////////////////////////////////////////////////////////////
    //      Реализация CRUD спортсменов        //
    /////////////////////////////////////////////////////////////////////////////////


    @Transactional
    public Sportsman saveSportsman(Sportsman sportsman) {
        sportsman.setRole(Role.SPORTSMAN);
        sportsman.setStatus(Status.ACTIVE);

        return sportsmanRepository.save(sportsman);
    }

    //Метод, позволяющий захешировать пароль при создании спортсмена Администратором
    @Transactional
    public void hashPassword(SportsmanDTO sportsmanDTO) {
        sportsmanDTO.setPassword(passwordEncoder.encode(sportsmanDTO.getPassword()));
    }

    //Метод для получения спортсмена (Sportsman) по email
    public Sportsman getSportsmanByEmail(String email) {
        return sportsmanRepository.findByEmail(email).orElse(null);
    }

    //Метод для получения спортсмена (Sportsman) по email
    public Sportsman getSportsmanById(String id) {
        return sportsmanRepository.findById(id).orElse(null);
    }

    //Метод для вывода всех спортсменов (Sportsman) из БД
    public List<Sportsman> showAllSportsmen() {
        return sportsmanRepository.findAll(Sort.by("surname"));
    }

    //Метод для блокировки user'a по id.
    @Transactional
    public User blockingUser(String id) {
        User user = userMainRepository.findById(id).orElse(null);
        assert user != null;
        user.setStatus(Status.BANNED);
        return user;
    }

    //Метод для разблокировки user'a по id.
    @Transactional
    public User unblockingUser(String id) {
        User user = userMainRepository.findById(id).orElse(null);
        assert user != null;
        user.setStatus(Status.ACTIVE);
        return user;
    }


    @Transactional
    public void editSportsman(String id, Sportsman updatedSportsman) {

        Sportsman sportsman = sportsmanRepository.findById(id).orElse(null);

        assert sportsman != null;
        sportsman.setFirstName(updatedSportsman.getFirstName());
        sportsman.setSurname(updatedSportsman.getSurname());
        sportsman.setPatronymic(updatedSportsman.getPatronymic());
        sportsman.setBirthDate(updatedSportsman.getBirthDate());
        sportsman.setRegion(updatedSportsman.getRegion());
        sportsman.setSex(updatedSportsman.getSex());
        sportsman.setSportsTitle(updatedSportsman.getSportsTitle());
        sportsman.setIsRegionalTeamSportsman(updatedSportsman.getIsRegionalTeamSportsman());
    }


    /////////////////////////////////////////////////////////////////////////////////
    //      Реализация CRUD соревнований        //
    /////////////////////////////////////////////////////////////////////////////////

    @Transactional
    public Competition createCompetition(Competition competition) {
        competition.setStatus(StatusOfCompetition.FUTURE);
        return competitionRepository.save(competition);
    }

    @Transactional
    public CompetitionCreateDTO editCompetition(String id, Competition updatedCompetition) {
        Competition competition = competitionRepository.findById(id).orElse(null);

        assert competition != null;
        competition.setName(updatedCompetition.getName());
        competition.setPlace(updatedCompetition.getPlace());
        competition.setType(updatedCompetition.getType());
        competition.setBowTypeList(updatedCompetition.getBowTypeList());
        competition.setStatus(updatedCompetition.getStatus());
        competition.setMainJudge(updatedCompetition.getMainJudge());
        competition.setSecretary(updatedCompetition.getSecretary());
        competition.setZamJudge(updatedCompetition.getZamJudge());
        competition.setJudges(updatedCompetition.getJudges());
        competition.setDate(updatedCompetition.getDate());
        competition.setEndDate(updatedCompetition.getEndDate());
        competition.setDescription(updatedCompetition.getDescription());
        return competitionMapper.fromCompetitionCraeteDTO(competition);
    }

    @Transactional
    public void changeStatusOfCompetition(String id) {
        Competition competition = competitionRepository.findById(id).orElse(null);
        assert competition != null;
        competition.setStatus(StatusOfCompetition.PRESENT);
    }

    @Transactional
    public void deleteCompetition(String id) {
        Competition competition = competitionRepository.findById(id).orElse(null);
        assert competition != null;
        competitionRepository.delete(competition);
    }

    @Transactional
    public void createProtocolForCompetition(Competition savedCompetition) {
        Protocol protocol = new Protocol();
        protocol.setCompetition(savedCompetition);
        protocolRepository.save(protocol);
    }

    @Transactional
    public Sportsman addSportsmanToRegionalTeam(String id) {
        Sportsman sportsman = sportsmanRepository.findById(id).orElse(null);
        if (sportsman == null) {
            return null;
        } else {
            sportsman.setIsRegionalTeamSportsman(true);
            sportsmanRepository.save(sportsman);
            return sportsman;
        }
    }

    @Transactional
    public Sportsman deleteFromRegionalTeam(String id) {
        Sportsman sportsman = sportsmanRepository.findById(id).orElse(null);
        if (sportsman == null) {
            return null;
        } else {
            sportsman.setIsRegionalTeamSportsman(false);
            sportsmanRepository.save(sportsman);
            return sportsman;
        }
    }

    @Transactional
    public List<Sportsman> getSportsmanByFio(String surname, String name, String patronymic) {
        if (surname == null && surname.isEmpty() && surname == null && surname.isEmpty() && surname == null && surname.isEmpty()) {
            return null;
        }
        return sportsmanRepositoryImpl.findSportsmenByParams(surname, name.toLowerCase(), patronymic.toLowerCase());

    }

    @Transactional
    public boolean addFilesToCompetition(String competitionId, MultipartFile[] files) {
        Competition competition = competitionRepository.findById(competitionId).orElse(null);
        if (competition == null) {
            return false;
        }
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
                    fileManagerPath + "/competition/uploadFiles?competitionId=" + competitionId,
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
                    competition.setPdfFile(String.join(", ", fileNames));
                } else {
                    competition.setPdfFile(null);
                }
                competitionRepository.save(competition);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }

        return false;
    }


    public boolean addFilesToRegionalFederation(String id, MultipartFile[] files) {
        RegionalTeam regionalTeam = regionalTeamRepository.findById(id).orElse(null);
        if (regionalTeam != null) {
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
                        fileManagerPath + "/regionalTeam/uploadFiles",
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
                        regionalTeam.setFileName(String.join(", ", fileNames));
                    } else {
                        regionalTeam.setFileName(null);
                    }
                    regionalTeamRepository.save(regionalTeam);
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