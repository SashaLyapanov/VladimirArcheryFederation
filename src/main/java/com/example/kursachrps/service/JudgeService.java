package com.example.kursachrps.service;

import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.Workbook;
import com.example.kursachrps.comparators.ApplicationComparator;
import com.example.kursachrps.models.*;
import com.example.kursachrps.mapper.SportsmanMapper;
import com.example.kursachrps.repositories.*;
import com.example.kursachrps.utils.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

@Service
public class JudgeService {
    private static final String MAN_ID = "c99ccd51-5731-42a3-9cfc-31cc4011e035";
    private static final String WOMAN_ID = "848f4054-a9c1-4525-9e10-2ab07e3e9b4c";

    private final ApplicationRepository applicationRepository;
    private final CompetitionRepository competitionRepository;
    private final UserMainRepository userMainRepository;
    private final SportsmanMapper sportsmanMapper;
    private final QualificationRoundRepository qualificationRoundRepository;
    private final QualificationRoundService qualificationRoundService;
    private final FileUtils fileUtils;
    private final ExcelGenerator excelGenerator;
    private final ProtocolRepository protocolRepository;
    private final ProtocolService protocolService;
    private final ProtocolStage2Service protocolStage2Service;
    private final ProtocolStage4Service protocolStage4Service;
    private final ProtocolStage8Service protocolStage8Service;
    private final ProtocolFinalService protocolFinalService;
    private final ProtocolStage2Repository protocolStage2Repository;
    private final ProtocolStage4Repository protocolStage4Repository;
    private final ProtocolStage8Repository protocolStage8Repository;
    private final ProtocolFinalRepository protocolFinalRepository;
    private final SexRepository sexRepository;

    @Autowired
    public JudgeService(ApplicationRepository applicationRepository,
                        CompetitionRepository competitionRepository,
                        UserMainRepository userMainRepository,
                        SportsmanMapper sportsmanMapper,
                        QualificationRoundRepository qualificationRoundRepository,
                        QualificationRoundService qualificationRoundService,
                        FileUtils fileUtils,
                        ExcelGenerator excelGenerator,
                        ProtocolRepository protocolRepository,
                        ProtocolService protocolService,
                        ProtocolStage2Service protocolStage2Service,
                        ProtocolStage4Service protocolStage4Service,
                        ProtocolStage8Service protocolStage8Service,
                        ProtocolFinalService protocolFinalService,
                        ProtocolStage2Repository protocolStage2Repository,
                        ProtocolStage4Repository protocolStage4Repository,
                        ProtocolStage8Repository protocolStage8Repository,
                        ProtocolFinalRepository protocolFinalRepository,
                        SexRepository sexRepository) {
        this.applicationRepository = applicationRepository;
        this.competitionRepository = competitionRepository;
        this.userMainRepository = userMainRepository;
        this.sportsmanMapper = sportsmanMapper;
        this.qualificationRoundRepository = qualificationRoundRepository;
        this.qualificationRoundService = qualificationRoundService;
        this.fileUtils = fileUtils;
        this.excelGenerator = excelGenerator;
        this.protocolRepository = protocolRepository;
        this.protocolService = protocolService;
        this.protocolStage2Service = protocolStage2Service;
        this.protocolStage4Service = protocolStage4Service;
        this.protocolStage8Service = protocolStage8Service;
        this.protocolFinalService = protocolFinalService;
        this.protocolStage2Repository = protocolStage2Repository;
        this.protocolStage4Repository = protocolStage4Repository;
        this.protocolStage8Repository = protocolStage8Repository;
        this.protocolFinalRepository = protocolFinalRepository;
        this.sexRepository = sexRepository;
    }

    @Value("${projectPath}")
    private String projectPath;

    /**
     * Метод для генерации EXCEL протокола 3D соревнований.
     */
    @Transactional
    public File generateProtocol(String competitionId) {
        if (competitionId == null || competitionId.equals("")) {
            return null;
        } else {
            LocalDate today = LocalDate.now();
//            File protocol = new File("C:/Users/-/IdeaProjects/VladimirArcheryFederation/src/filesExcel/" + today + ".xlsx");
            File protocol = new File(projectPath + "/src/filesExcel/" + today + ".xlsx");
            try {
                boolean created = protocol.createNewFile();
                if (created) {
                    System.out.println("Ес, исходный файл для работы создался");
                }
            } catch (IOException e) {
                System.out.println("Вероятнее всего файл с таким именем уже существует");
                System.out.println(e.getMessage());
            }

//            try (InputStream inputStream = new FileInputStream("C:/Users/-/IdeaProjects/VladimirArcheryFederation/src/filesExcel/Pattern.xlsx");
            try (InputStream inputStream = new FileInputStream(projectPath + "/src/filesExcel/Pattern.xlsx");
                 OutputStream outputStream = new FileOutputStream(protocol)) {
                inputStream.transferTo(outputStream);
            } catch (IOException e) {
                System.out.println("Произошла ошибка при копировании файла.");
                e.printStackTrace();
            }

            //Получение спортсменов, зарегистрированных на данные соревнования
            List<Application> applications = applicationRepository.findApplicationByCompetitionId(competitionId);

            sortApplicationsForQualifications(applications);

            try {
                excelGenerator.appendRowsForQualification(applications, protocol);
            } catch (IOException | InvalidFormatException e) {
                System.out.println("Провалилось заполнение протокола");
                e.printStackTrace();
            }
            System.out.println("Протокол успешно создан");


            return protocol;
        }
    }

    private List<Application> sortApplicationsForQualifications(List<Application> applications) {
        Collections.sort(applications, new ApplicationComparator());

        return applications;
    }

    /**
     * Метод для загрузки квалификационного протокола, после внесения в него результатов прохождения двух кругов
     */
    @Transactional
    public File uploadQualificationProtocol(MultipartFile file, String competitionId) {
        try (InputStream inputStream = new FileInputStream(fileUtils.convertMultipartFileToFile(file));
//             OutputStream outputStream = new FileOutputStream("C:/Users/-/IdeaProjects/VladimirArcheryFederation/src/filesExcel/" + file.getOriginalFilename())) {
             OutputStream outputStream = new FileOutputStream(projectPath + "/src/filesExcel/" + file.getOriginalFilename())) {
            inputStream.transferTo(outputStream);
            System.out.println("Файл успешно скопирован в исходник:" + file.getOriginalFilename());
            //Считывание данных из файла в сущнсоти QualificationRound и запись в БД
//            File fileProtocol = new File("C:/Users/-/IdeaProjects/VladimirArcheryFederation/src/filesExcel/" + file.getOriginalFilename());
            File fileProtocol = new File(projectPath + "/src/filesExcel/" + file.getOriginalFilename());
            List<QualificationRound> qualificationRoundList = excelGenerator.readQualificationToDB(fileProtocol, competitionId);
            qualificationRoundList = qualificationRoundService.calculateSportsmanPlaceInQualification(qualificationRoundList, competitionId);
            if (qualificationRoundList != null) {
                qualificationRoundRepository.saveAll(qualificationRoundList);
                Protocol protocol = protocolRepository.findProtocolByCompetitionId(competitionId);
                protocol.setQualification(true);
                protocolRepository.save(protocol);
                return fileProtocol;
            } else {
                return null;
            }
        } catch (IOException e) {
            System.out.println("Произошла ошибка при копировании файла.");
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public File uploadProtocolWithSomeStage(MultipartFile file, String competitionId) {
        try (InputStream inputStream = new FileInputStream(fileUtils.convertMultipartFileToFile(file));
             OutputStream outputStream = new FileOutputStream("C:/Users/-/IdeaProjects/VladimirArcheryFederation/src/filesExcel/" + file.getOriginalFilename())) {
            inputStream.transferTo(outputStream);
            System.out.println("Файл успешно скопирован в исходник:" + file.getOriginalFilename());
            //Считывание данных из файла в сущнсоти QualificationRound и запись в БД
//            File fileProtocol = new File("C:/Users/-/IdeaProjects/VladimirArcheryFederation/src/filesExcel/" + file.getOriginalFilename());
            File fileProtocol = new File(projectPath + "/src/filesExcel/" + file.getOriginalFilename());
            Protocol protocol = protocolRepository.findProtocolByCompetitionId(competitionId);
            List<ProtocolStage2> protocolStage2List = excelGenerator.readStage2ToDB(fileProtocol, competitionId, protocol);
            List<ProtocolStage4> protocolStage4List = excelGenerator.readStage4ToDB(fileProtocol, competitionId, protocol);
            List<ProtocolStage8> protocolStage8List = excelGenerator.readStage8ToDB(fileProtocol, competitionId, protocol);
            List<ProtocolFinal> protocolFinalList = excelGenerator.readFinalToDB(fileProtocol, competitionId, protocol);
            protocolStage2List = protocolStage2Service.calculateSportsmanPlaceInStage2(protocolStage2List, competitionId);
            protocolStage4List = protocolStage4Service.calculateSportsmanPlaceInStage4(protocolStage4List, competitionId);
            protocolStage8List = protocolStage8Service.calculateSportsmanPlaceInStage8(protocolStage8List, competitionId);
            protocolFinalList = protocolFinalService.calculateSportsmanPlaceInFinal(protocolFinalList, competitionId);
            excelGenerator.deleteThisStage(fileProtocol);
            if (protocolStage2List != null) {
                protocolStage2Repository.saveAll(protocolStage2List);
            }
            if (protocolStage4List != null) {
                protocolStage4Repository.saveAll(protocolStage4List);
            }
            if (protocolStage8List != null) {
                protocolStage8Repository.saveAll(protocolStage8List);
            }
            if (protocolFinalList != null) {
                protocolFinalRepository.saveAll(protocolFinalList);
            }
            if (fileProtocol.exists()) {
                return fileProtocol;
            } else {
                return null;
            }
        } catch (IOException e) {
            System.out.println("Произошла ошибка при копировании файла.");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Метод для генерации последующей стадии соревнований
     */
    @Transactional
    public boolean generateNextStageOfCompetitionAfterQualification(File file, String competitionId) {
        Protocol protocol = protocolRepository.findProtocolByCompetitionId(competitionId);
        if (!protocol.updateIsAllFlagsTrue()) {
            try (InputStream inputStream = new FileInputStream(file)) {
                Competition competition = competitionRepository.findById(competitionId).orElse(null);
                if (competition != null) {
                    List<BowType> bowTypeList = new ArrayList<>();
                    bowTypeList.addAll(competition.getBowTypeList());
                    for (BowType bowType : bowTypeList) {
                        List<QualificationRound> sportsmanListInBowType = qualificationRoundRepository.findQualificationRoundByCompetitionIdAndBowTypeId(competitionId, bowType.getId());
                        //Условие на протоколы мужчин для каждой стадии
                        List<QualificationRound> sportsmanMANListInBowType = qualificationRoundService.getSportsmanMANListInBowType(sportsmanListInBowType);
                        //Сортировка списка спортсменов по
                        Collections.sort(sportsmanMANListInBowType);
                        if (sportsmanMANListInBowType.size() > 16) {
                            //Генерируем 1/8 финала для данного класса лука
                            excelGenerator.generate8StageMAN(file, bowType, sportsmanMANListInBowType);
                            protocolService.setProtocolFieldTrueForMAN8(bowType, protocol);
                        } else if (sportsmanMANListInBowType.size() > 8) {
                            //Генерируем 1/4 финала для данного класса лука
                            excelGenerator.generate4StageMAN(file, bowType, sportsmanMANListInBowType);
                            protocolService.setProtocolFieldTrueForMAN4(bowType, protocol);
                            protocolService.setProtocolFieldTrueForMAN8(bowType, protocol);
                        } else if (sportsmanMANListInBowType.size() > 5) {
                            //Генерируем 1/2 финала для данного класса лука
                            //TODO
                            // переделать генерацию 1/2 финала под структуру 1-4 2-3
                            excelGenerator.generate2StageMAN(file, bowType, sportsmanMANListInBowType);
                            protocolService.setProtocolFieldTrueForMAN2(bowType, protocol);
                            protocolService.setProtocolFieldTrueForMAN4(bowType, protocol);
                            protocolService.setProtocolFieldTrueForMAN8(bowType, protocol);
                        } else if (sportsmanMANListInBowType.size() > 0) {
                            // Нужно считывать данные в финальную таблицу соревнований!
                            // Т.е. сразу же заполняем этими 5 людьми таблицу результата
                            AbstractMap.SimpleEntry<BowType, Sex> key = new AbstractMap.SimpleEntry<>(bowType, sexRepository.findById(MAN_ID).orElse(null));
                            Application application = new Application();
                            HashMap<AbstractMap.SimpleEntry<BowType, Sex>, Application> entry = new HashMap<>();
                            entry.put(key, application);
                            protocolService.markExtraStagesAfterQualification(protocol, entry);
                            System.out.println(entry);

                        }

                        //Условие на протоколы женщин для каждой стадии
                        List<QualificationRound> sportsmanWOMANListInBowType = qualificationRoundService.getSportsmanWOMANListInBowType(sportsmanListInBowType);
                        if (sportsmanWOMANListInBowType.size() > 16) {
                            //Генерируем 1/8 финала для данного класса лука
                            excelGenerator.generate8StageWOMAN(file, bowType, sportsmanWOMANListInBowType);
                            protocolService.setProtocolFieldTrueForWOMAN8(bowType, protocol);
                        } else if (sportsmanWOMANListInBowType.size() > 8) {
                            //Генерируем 1/4 финала для данного класса лука
                            excelGenerator.generate4StageWOMAN(file, bowType, sportsmanWOMANListInBowType);
                            protocolService.setProtocolFieldTrueForWOMAN4(bowType, protocol);
                            protocolService.setProtocolFieldTrueForWOMAN8(bowType, protocol);
                        } else if (sportsmanWOMANListInBowType.size() > 5) {
                            //Генерируем 1/2 финала для данного класса лука
                            //TODO
                            // переделать генерацию 1/2 финала под структуру 1-4 2-3
                            excelGenerator.generate2StageWOMAN(file, bowType, sportsmanWOMANListInBowType);
                            protocolService.setProtocolFieldTrueForWOMAN2(bowType, protocol);
                            protocolService.setProtocolFieldTrueForWOMAN4(bowType, protocol);
                            protocolService.setProtocolFieldTrueForWOMAN8(bowType, protocol);
                        } else if (sportsmanWOMANListInBowType.size() > 0) {
                            // Нужно считывать данные в финальную таблицу соревнований!
                            // Т.е. сразу же заполняем этими 5 людьми таблицу результата
                            AbstractMap.SimpleEntry<BowType, Sex> key = new AbstractMap.SimpleEntry<>(bowType, sexRepository.findById(WOMAN_ID).orElse(null));
                            Application application = new Application();
                            HashMap<AbstractMap.SimpleEntry<BowType, Sex>, Application> entry = new HashMap<>();
                            entry.put(key, application);
                            protocolService.markExtraStagesAfterQualification(protocol, entry);
                            System.out.println("entry " + entry);
                        }
                    }
                }
                return true;

            } catch (IOException e) {
                ResponseEntity.badRequest();
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Метод для генерации последующей стадии соревнований
     */
    @Transactional
    public boolean generateNextStageOfCompetition(File protocol, String competitionId) throws IOException {
        List<String> listsNamesForGenerating = protocolService.getStageForGenerating(competitionId);
        System.out.println("listsNamesForGenerating=");
        System.out.println(listsNamesForGenerating);

        if (listsNamesForGenerating != null) {
            for (String listName : listsNamesForGenerating) {
                excelGenerator.generateNextStageOfCompetition(listName, protocol, competitionId);
            }
            return true;
        }
        return false;
    }


    /**
     * Метод для загрузки файла на сервер (заменяет существующий, сгенерированный ранее протокол)
     */
    //TODO
    // Если оставлю этот метод, то нужно переделать не через today, а через название файла, который приходит на вход,
    // т.к. протокол может быть загружен на следующие сутки по каким-либо причинам!!!
    @Transactional
    public String uploadFile(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                LocalDate today = LocalDate.now();
//                File oldFile = new File("C:/Users/-/IdeaProjects/VladimirArcheryFederation/src/filesExcel/" + today + ".xlsx");
                File oldFile = new File(projectPath + "/src/filesExcel/" + today + ".xlsx");
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(oldFile));
                stream.write(bytes);
                stream.close();
                return oldFile.toString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }

    /**
     * Метод для преобразования xlsx в PDF
     */
    public String convertXLSXToPDF(String excelFileName) throws Exception {
        Workbook workbook = new Workbook(excelFileName);

        PdfSaveOptions options = new PdfSaveOptions();
        options.setOnePagePerSheet(true);

        LocalDate today = LocalDate.now();
//        workbook.save("C:/Users/-/IdeaProjects/KursachRPS/src/filePDF/" + today + ".pdf", options);
        workbook.save(projectPath + "/src/filePDF/" + today + ".pdf", options);

        return today + ".pdf";
    }

    /**
     * Метод для вывода списка соревнований, где status = Present
     */
    public List<Competition> getPresentCompetitions() {
        return competitionRepository.findAllPresent();
    }

    /**
     * Метод для регистрации спортсмена/тренера на соревнования
     */
    @Transactional
    public void registrateParticipantToCompetition(String email, String competitionId, Application application) {

        Competition competition = competitionRepository.findById(competitionId).orElse(null);
        User participant = userMainRepository.findByEmail(email).orElse(null);

        application.setCompetition(competition);
        assert participant != null;
        if (participant.getRole() == Role.SPORTSMAN) {
            Sportsman sportsman = sportsmanMapper.fromUser(participant);
            application.setSportsman(sportsman);
        }
//        application.setPayment(true);

        applicationRepository.save(application);
    }

    public void markExtraStages(String competitionId) {
        Protocol protocol = protocolRepository.findProtocolByCompetitionId(competitionId);
        List<Application> applicationList = applicationRepository.findApplicationByCompetitionId(competitionId);
        HashMap<AbstractMap.SimpleEntry<BowType, Sex>, Application> uniqueApplicationsSet = new HashMap<>();

        for (Application application : applicationList) {
            AbstractMap.SimpleEntry<BowType, Sex> key = new AbstractMap.SimpleEntry<>(application.getBowType(), application.getSportsman().getSex());
            uniqueApplicationsSet.put(key, application);
        }
        protocolService.markExtraStages(protocol, uniqueApplicationsSet);
    }

}
