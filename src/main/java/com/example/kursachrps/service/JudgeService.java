package com.example.kursachrps.service;

import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.Workbook;
import com.example.kursachrps.models.*;
import com.example.kursachrps.mapper.SportsmanMapper;
import com.example.kursachrps.repositories.ApplicationRepository;
import com.example.kursachrps.repositories.CompetitionRepository;
import com.example.kursachrps.repositories.QualificationRoundRepository;
import com.example.kursachrps.repositories.UserMainRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

@Service
public class JudgeService {

    private ApplicationRepository applicationRepository;
    private CompetitionRepository competitionRepository;
    private UserMainRepository userMainRepository;
    private SportsmanMapper sportsmanMapper;
    private QualificationRoundRepository qualificationRoundRepository;
    private FileUtils fileUtils;
    private ExcelGenerator excelGenerator;

    @Autowired
    public JudgeService(ApplicationRepository applicationRepository,
                        CompetitionRepository competitionRepository,
                        UserMainRepository userMainRepository,
                        SportsmanMapper sportsmanMapper,
                        QualificationRoundRepository qualificationRoundRepository,
                        FileUtils fileUtils,
                        ExcelGenerator excelGenerator) {
        this.applicationRepository = applicationRepository;
        this.competitionRepository = competitionRepository;
        this.userMainRepository = userMainRepository;
        this.sportsmanMapper = sportsmanMapper;
        this.qualificationRoundRepository = qualificationRoundRepository;
        this.fileUtils = fileUtils;
        this.excelGenerator = excelGenerator;
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
        //TODO
        // Реализовать метод разбиения на группы спортсменов по классу лука
        // Можно разбивать по следующему принципу:
        // Смотрим сколько заявлено человек, ЕСЛИ деление с остатком на

        return applications;
    }

    /**
     * Метод для загрузки квалификационного протокола, после внесения в него результатов прохождения двух кругов
     */
    @Transactional
    public void uploadQualificationProtocol(MultipartFile file, String competitionId) {
        try (InputStream inputStream = new FileInputStream(fileUtils.convertMultipartFileToFile(file));
             OutputStream outputStream = new FileOutputStream("C:/Users/-/IdeaProjects/VladimirArcheryFederation/src/filesExcel/" + file.getOriginalFilename())) {
            inputStream.transferTo(outputStream);
            System.out.println("Файл успешно скопирован в исходник:" + file.getOriginalFilename());
            //Считывание данных из файла в сущнсоти QualificationRound и запись в БД
            File protocol = new File("C:/Users/-/IdeaProjects/VladimirArcheryFederation/src/filesExcel/" + file.getOriginalFilename());
            List<QualificationRound> qualificationRoundList = excelGenerator.readQualificationToDB(protocol, competitionId);
            if (qualificationRoundList != null) {
                for (QualificationRound qualificationRound: qualificationRoundList) {
                    qualificationRoundRepository.save(qualificationRound);
                }
            }
        } catch (IOException e) {
            System.out.println("Произошла ошибка при копировании файла.");
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
