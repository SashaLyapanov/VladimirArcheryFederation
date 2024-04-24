package com.example.kursachrps.service;

import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.Workbook;
import com.example.kursachrps.comparators.ApplicationComparator;
import com.example.kursachrps.models.*;
import com.example.kursachrps.mapper.SportsmanMapper;
import com.example.kursachrps.repositories.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class JudgeService {

    private ApplicationRepository applicationRepository;
    private CompetitionRepository competitionRepository;
    private UserMainRepository userMainRepository;
    private SportsmanMapper sportsmanMapper;
    private QualificationRoundRepository qualificationRoundRepository;
    private QualificationRoundService qualificationRoundService;
    private FileUtils fileUtils;
    private ExcelGenerator excelGenerator;
    private ProtocolRepository protocolRepository;
    private ProtocolService protocolService;

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
                        ProtocolService protocolService) {
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
    }

    /**
     * Метод для генерации EXCEL протокола 3D соревнований.
     */
    @Transactional
    public File generateProtocol(String competitionId) {
        if (competitionId == null || competitionId.equals("")) {
            return null;
        } else {
            LocalDate today = LocalDate.now();
            File protocol = new File("C:/Users/-/IdeaProjects/VladimirArcheryFederation/src/filesExcel/" + today + ".xlsx");
            try {
                boolean created = protocol.createNewFile();
                if (created) {
                    System.out.println("Ес, исходный файл для работы создался");
                }
            } catch (IOException e) {
                System.out.println("Вероятнее всего файл с таким именем уже существует");
                System.out.println(e.getMessage());
            }

            try (InputStream inputStream = new FileInputStream("C:/Users/-/IdeaProjects/VladimirArcheryFederation/src/filesExcel/Pattern.xlsx");
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
             OutputStream outputStream = new FileOutputStream("C:/Users/-/IdeaProjects/VladimirArcheryFederation/src/filesExcel/" + file.getOriginalFilename())) {
            inputStream.transferTo(outputStream);
            System.out.println("Файл успешно скопирован в исходник:" + file.getOriginalFilename());
            //Считывание данных из файла в сущнсоти QualificationRound и запись в БД
            File protocol = new File("C:/Users/-/IdeaProjects/VladimirArcheryFederation/src/filesExcel/" + file.getOriginalFilename());
            List<QualificationRound> qualificationRoundList = excelGenerator.readQualificationToDB(protocol, competitionId);
            qualificationRoundList = qualificationRoundService.calculateSportsmanPlaceInQualification(qualificationRoundList, competitionId);
            if (qualificationRoundList != null) {
                qualificationRoundRepository.saveAll(qualificationRoundList);
                return protocol;
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
    public void generateNextStageOfCompetition(File file, String competitionId) {
        /**
         * Обращаемся к файлу протокола, который сохранился на сервере в папке filesExcel
         * Далее создаем список List<BowType> bowTypeList, который получаем из таблицы qualificationRound по competitionId
         * Далее для каждого из bowTypeList через цикл foreach прописываем условия для генерации 1/8 / 1/4 / 1/2
         * Нужно создать пока что 2 таблицы в БД: (1/8 и 1/4), в которых будем хранить результаты данных стадий
         */

        try (InputStream inputStream = new FileInputStream(file)) {
            Competition competition = competitionRepository.findById(competitionId).orElse(null);
            if (competition != null) {
                List<BowType> bowTypeList = new ArrayList<>();
                bowTypeList.addAll(competition.getBowTypeList());
                for (BowType bowType: bowTypeList) {
                    List<QualificationRound> sportsmanListInBowType = qualificationRoundRepository.findQualificationRoundByCompetitionIdAndBowTypeId(competitionId, bowType.getId());
                    Protocol protocol = protocolRepository.findProtocolByCompetitionId(competitionId);
                    //Условие на протоколы мужчин для каждой стадии
                    List<QualificationRound> sportsmanMANListInBowType = qualificationRoundService.getSportsmanMANListInBowType(sportsmanListInBowType);
                    //Сортировка списка спортсменов по
                    Collections.sort(sportsmanMANListInBowType);
                    if (sportsmanMANListInBowType.size() > 16) {
                        //Генерируем 1/8 финала для данного класса лука
                        excelGenerator.generate8StageMAN(inputStream, bowType, sportsmanMANListInBowType);
                        protocolService.setProtocolFieldTrueForMAN8(bowType, protocol);
                    } else if (sportsmanMANListInBowType.size() > 8) {
                        //Генерируем 1/4 финала для данного класса лука
                        excelGenerator.generate4StageMAN(inputStream, bowType, sportsmanMANListInBowType);
                        protocolService.setProtocolFieldTrueForMAN4(bowType, protocol);
                        protocolService.setProtocolFieldTrueForMAN8(bowType, protocol);
                    } else if (sportsmanMANListInBowType.size() > 5) {
                        //Генерируем 1/2 финала для данного класса лука
                        excelGenerator.generate2StageMAN(inputStream, bowType, sportsmanMANListInBowType);
                        protocolService.setProtocolFieldTrueForMAN2(bowType, protocol);
                        protocolService.setProtocolFieldTrueForMAN4(bowType, protocol);
                        protocolService.setProtocolFieldTrueForMAN8(bowType, protocol);
                    } else {
                        //Генерируем итоговый результат по квалификационным результатам
//                        excelGenerator.generateFinal(inputStream, bowType, sportsmanMANListInBowType);
                    }

                    //Условие на протоколы женщин для каждой стадии
                    List<QualificationRound> sportsmanWOMANListInBowType = qualificationRoundService.getSportsmanWOMANListInBowType(sportsmanListInBowType);
                    if (sportsmanWOMANListInBowType.size() > 16) {
                        //Генерируем 1/8 финала для данного класса лука
                        excelGenerator.generate8StageWOMAN(inputStream, bowType, sportsmanWOMANListInBowType);
                        protocolService.setProtocolFieldTrueForWOMAN8(bowType, protocol);
                    } else if (sportsmanWOMANListInBowType.size() > 8) {
                        //Генерируем 1/4 финала для данного класса лука
                        excelGenerator.generate4StageWOMAN(inputStream, bowType, sportsmanWOMANListInBowType);
                        protocolService.setProtocolFieldTrueForWOMAN4(bowType, protocol);
                        protocolService.setProtocolFieldTrueForWOMAN8(bowType, protocol);
                    } else if (sportsmanWOMANListInBowType.size() > 5) {
                        //Генерируем 1/2 финала для данного класса лука
                        excelGenerator.generate2StageWOMAN(inputStream, bowType, sportsmanWOMANListInBowType);
                        protocolService.setProtocolFieldTrueForWOMAN2(bowType, protocol);
                        protocolService.setProtocolFieldTrueForWOMAN4(bowType, protocol);
                        protocolService.setProtocolFieldTrueForWOMAN8(bowType, protocol);
                    } else {
                        //Генерируем итоговый результат по квалификационным результатам
//                        excelGenerator.generateFinal(inputStream, bowType, sportsmanWOMANListInBowType);
                    }


                }
            }


        } catch (IOException e) {
            System.out.println("Произошла ошибка при работе с файлом");
            e.printStackTrace();
        }
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
                File oldFile = new File("C:/Users/-/IdeaProjects/VladimirArcheryFederation/src/filesExcel/" + today + ".xlsx");
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
        workbook.save("C:/Users/-/IdeaProjects/KursachRPS/src/filePDF/" + today + ".pdf", options);

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
        if (participant.getRole() == Role.SPORTSMAN) {
            Sportsman sportsman = sportsmanMapper.fromUser(participant);
            application.setSportsman(sportsman);
        }
//        application.setPayment(true);

        applicationRepository.save(application);
    }

    @Transactional
    public void addPathFileInCompetition(String competitionId, String name) {
        Competition competition = competitionRepository.findById(competitionId).orElse(null);
        assert competition != null;
        competition.setPdfFile(name);
    }


    @Transactional
    public void changeStatusOfCompetition(String competitionId) {
        Competition competition = competitionRepository.findById(competitionId).orElse(null);
        assert competition != null;
        competition.setStatus(StatusOfCompetition.PAST);
    }

}
