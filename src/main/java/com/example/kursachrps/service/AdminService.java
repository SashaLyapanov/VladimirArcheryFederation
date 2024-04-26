package com.example.kursachrps.service;

import com.example.kursachrps.models.*;
import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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


    private final SportsmanRepository sportsmanRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMainRepository userMainRepository;
    private final CompetitionRepository competitionRepository;
    private final ProtocolRepository protocolRepository;

    @Autowired
    public AdminService(SportsmanRepository sportsmanRepository,
                        PasswordEncoder passwordEncoder,
                        UserMainRepository userMainRepository,
                        CompetitionRepository competitionRepository,
                        ProtocolRepository protocolRepository) {
        this.sportsmanRepository = sportsmanRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMainRepository = userMainRepository;
        this.competitionRepository = competitionRepository;
        this.protocolRepository = protocolRepository;
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
    public Competition editCompetition(String id, Competition updatedCompetition) {
        Competition competition = competitionRepository.findById(id).orElse(null);

        assert competition != null;
        competition.setName(updatedCompetition.getName());
        competition.setPlace(updatedCompetition.getPlace());
        competition.setType(updatedCompetition.getType());
        competition.setCategories(updatedCompetition.getCategories());
        competition.setBowTypeList(updatedCompetition.getBowTypeList());
        competition.setMainJudge(updatedCompetition.getMainJudge());
        competition.setSecretary(updatedCompetition.getSecretary());
        competition.setZamJudge(updatedCompetition.getZamJudge());
        competition.setJudges(updatedCompetition.getJudges());
        competition.setDate(updatedCompetition.getDate());
        competition.setStatus(StatusOfCompetition.FUTURE);
        return competition;
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
        List<String> bowTypeList = new ArrayList<>();
        for (BowType bowType : savedCompetition.getBowTypeList()) {
            bowTypeList.add(bowType.getId());
        }
        if (!bowTypeList.contains(BL_3D)) {
            protocol.setBlock3DMan8(true);
            protocol.setBlock3DMan4(true);
            protocol.setBlock3DMan2(true);
            protocol.setBlock3DManFinal(true);
            protocol.setBlock3DWoman8(true);
            protocol.setBlock3DWoman4(true);
            protocol.setBlock3DWoman2(true);
            protocol.setBlock3DWomanFinal(true);
        }
        if (!bowTypeList.contains(CL_3D)) {
            protocol.setClassic3DMan8(true);
            protocol.setClassic3DMan4(true);
            protocol.setClassic3DMan2(true);
            protocol.setClassic3DManFinal(true);
            protocol.setClassic3DWoman8(true);
            protocol.setClassic3DWoman4(true);
            protocol.setClassic3DWoman2(true);
            protocol.setClassic3DWomanFinal(true);
        }
        if (!bowTypeList.contains(LongBow_3D)) {
            protocol.setLong3DMan8(true);
            protocol.setLong3DMan4(true);
            protocol.setLong3DMan2(true);
            protocol.setLong3DManFinal(true);
            protocol.setLong3DWoman8(true);
            protocol.setLong3DWoman4(true);
            protocol.setLong3DWoman2(true);
            protocol.setLong3DWomanFinal(true);
        }
        if (!bowTypeList.contains(CompositeBow_3D)) {
            protocol.setComposite3DMan8(true);
            protocol.setComposite3DMan4(true);
            protocol.setComposite3DMan2(true);
            protocol.setComposite3DManFinal(true);
            protocol.setComposite3DWoman8(true);
            protocol.setComposite3DWoman4(true);
            protocol.setComposite3DWoman2(true);
            protocol.setComposite3DWomanFinal(true);
        }
        if (!bowTypeList.contains(Sporting)) {
            protocol.setSporting3DMan8(true);
            protocol.setSporting3DMan4(true);
            protocol.setSporting3DMan2(true);
            protocol.setSporting3DManFinal(true);
            protocol.setSporting3DWoman8(true);
            protocol.setSporting3DWoman4(true);
            protocol.setSporting3DWoman2(true);
            protocol.setSporting3DWomanFinal(true);
        }
        if (!bowTypeList.contains(HistoryBow)) {
            protocol.setHistoryBow3DMan8(true);
            protocol.setHistoryBow3DMan4(true);
            protocol.setHistoryBow3DMan2(true);
            protocol.setHistoryBow3DManFinal(true);
            protocol.setHistoryBow3DWoman8(true);
            protocol.setHistoryBow3DWoman4(true);
            protocol.setHistoryBow3DWoman2(true);
            protocol.setHistoryBow3DWomanFinal(true);
        }
        if (!bowTypeList.contains(Olympic)) {
            protocol.setOlympic3DMan8(true);
            protocol.setOlympic3DMan4(true);
            protocol.setOlympic3DMan2(true);
            protocol.setOlympic3DManFinal(true);
            protocol.setOlympic3DWoman8(true);
            protocol.setOlympic3DWoman4(true);
            protocol.setOlympic3DWoman2(true);
            protocol.setOlympic3DWomanFinal(true);
        }
        if (!bowTypeList.contains(Arbalet)) {
            protocol.setArbalet3DMan8(true);
            protocol.setArbalet3DMan4(true);
            protocol.setArbalet3DMan2(true);
            protocol.setArbalet3DManFinal(true);
            protocol.setArbalet3DWoman8(true);
            protocol.setArbalet3DWoman4(true);
            protocol.setArbalet3DWoman2(true);
            protocol.setArbalet3DWomanFinal(true);
        }
        protocolRepository.save(protocol);
    }
}