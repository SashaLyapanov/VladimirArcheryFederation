package com.example.kursachrps.service;

import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.Workbook;
import com.example.kursachrps.ExcelGenerator2;
import com.example.kursachrps.models.*;
import com.example.kursachrps.mapper.SportsmanMapper;
import com.example.kursachrps.repositories.ApplicationRepository;
import com.example.kursachrps.repositories.CompetitionRepository;
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

    private ExcelGenerator2 excelGenerator2 = new ExcelGenerator2();


    @Autowired
    public JudgeService(ApplicationRepository applicationRepository,
                        CompetitionRepository competitionRepository,
                        UserMainRepository userMainRepository,
                        SportsmanMapper sportsmanMapper) {
        this.applicationRepository = applicationRepository;
        this.competitionRepository = competitionRepository;
        this.userMainRepository = userMainRepository;
        this.sportsmanMapper = sportsmanMapper;
    }

    /**
     * Метод для генерации EXCEL протокола 3D соревнований.
     */
    @Transactional
    public File generateProtocol(String competitionId) throws IOException {
        //Это наш шаблон, чтобы скопировать его в новый файл
        File file = new File("C:\\Users\\-\\IdeaProjects\\KursachRPS\\src\\filesExcel\\TestPattern.xlsx");
        //Создадим новый файл
        LocalDate today = LocalDate.now();
        File protocol = new File("C:/Users/-/IdeaProjects/KursachRPS/src/filesExcel/" + today + ".xlsx");
        try {
            boolean created = protocol.createNewFile();
            if (created) {
                System.out.println("Ес, файл создался");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        if (protocol.exists()) {
            InputStream is = null;
            OutputStream os = null;
            try {
                is = new FileInputStream(file);
                os = new FileOutputStream(protocol);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            } finally {
                assert is != null;
                is.close();
                assert os != null;
                os.close();
            }

            List<Application> applications = applicationRepository.findApplicationByCompetitionId(competitionId);

            try {
                excelGenerator2.appendRows(applications, protocol);
            } catch (IOException | InvalidFormatException e) {
                e.printStackTrace();
            }
            System.out.println("Протокол успешно создан");
        }

        return protocol;
    }

    /**
     * Метод для загрузки файла на сервер (заменяет существующий, сгенерированный ранее протокол)
     */
    @Transactional
    public String uploadFile(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                LocalDate today = LocalDate.now();
                File oldFile = new File("C:/Users/-/IdeaProjects/KursachRPS/src/filesExcel/" + today + ".xlsx");
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
        application.setPayment(true);

        applicationRepository.save(application);
    }

    @Transactional
    public void addPathFileInCompetition(int competitionId, String name) {
        Competition competition = competitionRepository.findById(competitionId).orElse(null);
        assert competition != null;
        competition.setPdfFile(name);
    }


    @Transactional
    public void changeStatusOfCompetition(int competitionId) {
        Competition competition = competitionRepository.findById(competitionId).orElse(null);
        assert competition != null;
        competition.setStatus(StatusOfCompetition.PAST);
    }
}
