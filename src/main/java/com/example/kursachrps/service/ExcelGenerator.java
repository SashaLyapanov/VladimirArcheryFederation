package com.example.kursachrps.service;

import com.example.kursachrps.models.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.*;

@Service
public class ExcelGenerator {
    private static final String LongBow_3D = "af44dbd5-21bb-41f1-b732-af5706b8153d";
    private static final String CompositeBow_3D = "62cb799b-0ff8-4843-82c0-61a215d4af97";
    private static final String CL_3D = "351c3a7e-64b4-4749-b8c8-bb1ecb2f3ef2";
    private static final String BL_3D = "ac6a3094-b354-4ebd-8bb1-19111742c764";
    private static final String Sporting = "62cb799b-0ff8-4843-b732-af5706b8153d";
    private static final String HistoryBow = "26454f95-0e38-45d4-a85e-dd37f4a04944";
    private static final String Olympic = "36671015-5c37-4e5c-8eed-9a353e927f32";
    private static final String Arbalet = "e6dc3841-98a3-4357-a139-61e48ac393e2";

    private ParserExcelData parserExcelData;

    @Autowired
    public ExcelGenerator(ParserExcelData parserExcelData) {
        this.parserExcelData = parserExcelData;
    }

    /**
     * Метод, который отвечает за подготовку данных из входящего списка сущностей в мэпу для вставки в Excel файл
     */
    private static Map<Integer, Object[]> prepareData(int rowNum, List<?> recordList) {
        Map<Integer, Object[]> data = new HashMap<>();
        if (recordList != null && recordList.size() > 0) {
            Object firstElement = recordList.get(0);
            if (firstElement instanceof Application) {
                List<Application> applications = (List<Application>) recordList;
                for (Application application : applications) {
                    rowNum++;
                    if (application.getSportsman() != null) {
                        Sportsman sportsman = application.getSportsman();
                        Sex sexName = sportsman.getSex();
                        SportsTitle sportsTitle = sportsman.getSportsTitle();
                        Region region = sportsman.getRegion();
                        BowType bowType = application.getBowType();
                        if (sexName != null && sportsTitle != null && region != null) {
                            data.put(rowNum, new Object[]{sportsman.getSurname() + " " + sportsman.getFirstName() + " " + sportsman.getPatronymic(),
                                    sexName.getName(), sportsman.getBirthDate(), sportsTitle.getName(), region.getName(), bowType.getBowTypeName()});
                        }
                    }
                }
            } else if (firstElement instanceof QualificationRound) {
                List<QualificationRound> qualificationRoundList = (List<QualificationRound>) recordList;
                for (QualificationRound qualificationRound : qualificationRoundList) {
                    rowNum++;
                    if (qualificationRound.getSportsman() != null) {
                        Sportsman sportsman = qualificationRound.getSportsman();
                        Sex sexName = sportsman.getSex();
                        SportsTitle sportsTitle = sportsman.getSportsTitle();
                        Region region = sportsman.getRegion();
                        BowType bowType = qualificationRound.getBowType();
                        int pointInQualification = qualificationRound.getSum();
                        if (sexName != null && sportsTitle != null && region != null) {
                            data.put(rowNum, new Object[]{sportsman.getSurname() + " " + sportsman.getFirstName() + " " + sportsman.getPatronymic(),
                                    sexName.getName(), sportsman.getBirthDate(), sportsTitle.getName(), region.getName(), bowType.getBowTypeName(), pointInQualification});
                        }
                    }
                }
            }
        }
        return data;
    }


    public void makeFontAndStyle(XSSFWorkbook workbook, CellStyle style, Font font) {
        font.setFontHeightInPoints((short) 14);
        font.setFontName("Times New Roman");
        style.setFont(font);
        //Стиль для даты
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("yyyy"));
        //Стиль для рамки вокруг ячеек
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
    }

    //Функция для записи строк в excel
    public void appendRowsForQualification(List<Application> applications, File file) throws IOException, InvalidFormatException {
        //Значение 4 четко под формат Pattern.xlsx!!!!!!
        int rowNum = 4;

        Map<Integer, Object[]> data = prepareData(rowNum, applications);

        writeDataToExcel("Квалификация", rowNum, data, file);
    }

    @Transactional
    public List<QualificationRound> readQualificationToDB(File protocol, String competitionId) throws IOException {
        List<QualificationRound> qualificationRoundList = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(protocol));
        XSSFSheet sheet = workbook.getSheetAt(0);

        //Значение 4 четко под формат Pattern.xlsx
        int rowNum = 4;

        while (true) {
            Row row = sheet.getRow(rowNum);
            if (row != null) {
                Cell cell = row.getCell(0);

                if (cell != null) {
                    QualificationRound qualificationRound = new QualificationRound();
                    qualificationRound.setCompetition(parserExcelData.findCompetitionById(competitionId));
                    for (int i = 1; i <= 11; i++) {
                        Cell qualificationData = row.getCell(i);
                        if (i == 1) {
                            //Спортсмен
                            Cell birthDate = row.getCell(3);
                            Sportsman sportsman = parserExcelData.findSportsmanByFioAndBirthDate(qualificationData, birthDate);
                            qualificationRound.setSportsman(sportsman);
                        } else if (i == 6) {
                            //Класс лука
                            BowType bowType = parserExcelData.findBowTypeByBowTypeName(qualificationData.toString());
                            qualificationRound.setBowType(bowType);
                        } else if (i == 7) {
                            //dist1
                            double cellValue = qualificationData.getNumericCellValue();
                            qualificationRound.setDist1((int) cellValue);
                        } else if (i == 8) {
                            //dist2
                            double cellValue = qualificationData.getNumericCellValue();
                            qualificationRound.setDist2((int) cellValue);
                        } else if (i == 9) {
                            qualificationRound.setSum(qualificationRound.getDist1() + qualificationRound.getDist2());
                            Sex sex = parserExcelData.findSexByName(row.getCell(2));
                            qualificationRound.setSportsTitle(parserExcelData.findSportsTitleBySumInQualification(sex, qualificationRound.getBowType(), qualificationRound.getSum()));
                        } else if (i == 10) {
                            //количество 11 за два круга
                            double cellValue = qualificationData.getNumericCellValue();
                            qualificationRound.setQuantity11((int) cellValue);
                        } else if (i == 11) {
                            //количество 10 за два круга
                            double cellValue = qualificationData.getNumericCellValue();
                            qualificationRound.setQuantity10((int) cellValue);
                        }
                    }
                    qualificationRoundList.add(qualificationRound);
                    rowNum++;
                } else {
                    break;
                }
            } else {
                break;
            }

        }

        return qualificationRoundList;

    }

    /**
     * Метод для генерации 1/8 финала у мужчин
     */
    public void generate8StageMAN(File file, BowType bowType, List<QualificationRound> sportsmanMANListInBowType) throws IOException {
        List<QualificationRound> lidersSportsmanList = new ArrayList<>(sportsmanMANListInBowType.subList(0, 16));
        String excelNameList = "";
        if (Objects.equals(bowType.getId(), BL_3D)) {
            excelNameList = "1,8финала 3Д_БЛ_Муж";
        } else if (Objects.equals(bowType.getId(), CL_3D)) {
            excelNameList = "1,8финала 3Д_КЛ_Муж";
        } else if (Objects.equals(bowType.getId(), LongBow_3D)) {
            excelNameList = "1,8финала 3Д-Long_Муж";
        } else if (Objects.equals(bowType.getId(), CompositeBow_3D)) {
            excelNameList = "1,8финала 3Д-Составной_Муж";
        } else if (Objects.equals(bowType.getId(), Sporting)) {
            excelNameList = "1,8финала 3Д-Sporting_Муж";
        } else if (Objects.equals(bowType.getId(), HistoryBow)) {
            excelNameList = "1,8финала 3Д-Исторический_Муж";
        } else if (Objects.equals(bowType.getId(), Olympic)) {
            excelNameList = "1,8финала 3Д-Олимпик_Муж";
        } else if (Objects.equals(bowType.getId(), Arbalet)) {
            excelNameList = "1,8финала 3Д-Арбалет_Муж";
        }

        //Значение 4 четко под формат Pattern.xlsx!!!!!!
        int rowNum = 4;

        Map<Integer, Object[]> data = prepareData(rowNum, lidersSportsmanList);

        writeDataToExcel(excelNameList, rowNum, data, file);
    }

    /**
     * Метод для генерации 1/4 финала у мужчин
     */
    public void generate4StageMAN(File file, BowType bowType, List<QualificationRound> sportsmanMANListInBowType) throws IOException {
        List<QualificationRound> lidersSportsmanList = new ArrayList<>(sportsmanMANListInBowType.subList(0, 8));
        String excelNameList = "";
        if (Objects.equals(bowType.getId(), BL_3D)) {
            excelNameList = "1,4финала 3Д_БЛ_Муж";
        } else if (Objects.equals(bowType.getId(), CL_3D)) {
            excelNameList = "1,4финала 3Д_КЛ_Муж";
        } else if (Objects.equals(bowType.getId(), LongBow_3D)) {
            excelNameList = "1,4финала 3Д-Long_Муж";
        } else if (Objects.equals(bowType.getId(), CompositeBow_3D)) {
            excelNameList = "1,4финала 3Д-Составной_Муж";
        } else if (Objects.equals(bowType.getId(), Sporting)) {
            excelNameList = "1,4финала 3Д-Sporting_Муж";
        } else if (Objects.equals(bowType.getId(), HistoryBow)) {
            excelNameList = "1,4финала 3Д-Исторический_Муж";
        } else if (Objects.equals(bowType.getId(), Olympic)) {
            excelNameList = "1,4финала 3Д-Олимпик_Муж";
        } else if (Objects.equals(bowType.getId(), Arbalet)) {
            excelNameList = "1,4финала 3Д-Арбалет_Муж";
        }

        //Значение 4 четко под формат Pattern.xlsx!!!!!!
        int rowNum = 4;

        Map<Integer, Object[]> data = prepareData(rowNum, lidersSportsmanList);

        writeDataToExcel(excelNameList, rowNum, data, file);
    }

    /**
     * Метод для генерации 1/2 финала у мужчин
     */
    public void generate2StageMAN(File file, BowType bowType, List<QualificationRound> sportsmanMANListInBowType) throws IOException {
        List<QualificationRound> lidersSportsmanList = new ArrayList<>(sportsmanMANListInBowType.subList(0, 4));
        String excelNameList = "";
        if (Objects.equals(bowType.getId(), BL_3D)) {
            excelNameList = "1,2финала 3Д_БЛ_Муж";
        } else if (Objects.equals(bowType.getId(), CL_3D)) {
            excelNameList = "1,2финала 3Д_КЛ_Муж";
        } else if (Objects.equals(bowType.getId(), LongBow_3D)) {
            excelNameList = "1,2финала 3Д-Long_Муж";
        } else if (Objects.equals(bowType.getId(), CompositeBow_3D)) {
            excelNameList = "1,2финала 3Д-Составной_Муж";
        } else if (Objects.equals(bowType.getId(), Sporting)) {
            excelNameList = "1,2финала 3Д-Sporting_Муж";
        } else if (Objects.equals(bowType.getId(), HistoryBow)) {
            excelNameList = "1,2финала 3Д-Исторический_Муж";
        } else if (Objects.equals(bowType.getId(), Olympic)) {
            excelNameList = "1,2финала 3Д-Олимпик_Муж";
        } else if (Objects.equals(bowType.getId(), Arbalet)) {
            excelNameList = "1,2финала 3Д-Арбалет_Муж";
        }

        //Значение 4 четко под формат Pattern.xlsx!!!!!!
        int rowNum = 4;

        Map<Integer, Object[]> data = prepareData(rowNum, lidersSportsmanList);

        writeDataToExcel(excelNameList, rowNum, data, file);
    }

    /**
     * Метод для генерации 1/8 финала у женщин
     */
    public void generate8StageWOMAN(File file, BowType bowType, List<QualificationRound> sportsmanWOMANListInBowType) throws IOException {
        List<QualificationRound> lidersSportsmanList = new ArrayList<>(sportsmanWOMANListInBowType.subList(0, 16));
        String excelNameList = "";
        if (Objects.equals(bowType.getId(), BL_3D)) {
            excelNameList = "1,8финала 3Д_БЛ_Жен";
        } else if (Objects.equals(bowType.getId(), CL_3D)) {
            excelNameList = "1,8финала 3Д_КЛ_Жен";
        } else if (Objects.equals(bowType.getId(), LongBow_3D)) {
            excelNameList = "1,8финала 3Д-Long_Жен";
        } else if (Objects.equals(bowType.getId(), CompositeBow_3D)) {
            excelNameList = "1,8финала 3Д-Составной_Жен";
        } else if (Objects.equals(bowType.getId(), Sporting)) {
            excelNameList = "1,8финала 3Д-Sporting_Жен";
        } else if (Objects.equals(bowType.getId(), HistoryBow)) {
            excelNameList = "1,8финала 3Д-Исторический_Жен";
        } else if (Objects.equals(bowType.getId(), Olympic)) {
            excelNameList = "1,8финала 3Д-Олимпик_Жен";
        } else if (Objects.equals(bowType.getId(), Arbalet)) {
            excelNameList = "1,8финала 3Д-Арбалет_Жен";
        }

        //Значение 4 четко под формат Pattern.xlsx!!!!!!
        int rowNum = 4;

        Map<Integer, Object[]> data = prepareData(rowNum, lidersSportsmanList);

        writeDataToExcel(excelNameList, rowNum, data, file);
    }

    /**
     * Метод для генерации 1/4 финала у женщин
     */
    public void generate4StageWOMAN(File file, BowType bowType, List<QualificationRound> sportsmanWOMANListInBowType) throws IOException {
        List<QualificationRound> lidersSportsmanList = new ArrayList<>(sportsmanWOMANListInBowType.subList(0, 8));
        String excelNameList = "";
        if (Objects.equals(bowType.getId(), BL_3D)) {
            excelNameList = "1,4финала 3Д_БЛ_Жен";
        } else if (Objects.equals(bowType.getId(), CL_3D)) {
            excelNameList = "1,4финала 3Д_КЛ_Жен";
        } else if (Objects.equals(bowType.getId(), LongBow_3D)) {
            excelNameList = "1,4финала 3Д-Long_Жен";
        } else if (Objects.equals(bowType.getId(), CompositeBow_3D)) {
            excelNameList = "1,4финала 3Д-Составной_Жен";
        } else if (Objects.equals(bowType.getId(), Sporting)) {
            excelNameList = "1,4финала 3Д-Sporting_Жен";
        } else if (Objects.equals(bowType.getId(), HistoryBow)) {
            excelNameList = "1,4финала 3Д-Исторический_Жен";
        } else if (Objects.equals(bowType.getId(), Olympic)) {
            excelNameList = "1,4финала 3Д-Олимпик_Жен";
        } else if (Objects.equals(bowType.getId(), Arbalet)) {
            excelNameList = "1,4финала 3Д-Арбалет_Жен";
        }

        //Значение 4 четко под формат Pattern.xlsx!!!!!!
        int rowNum = 4;

        Map<Integer, Object[]> data = prepareData(rowNum, lidersSportsmanList);

        writeDataToExcel(excelNameList, rowNum, data, file);
    }

    /**
     * Метод для генерации 1/2 финала у мужчин
     */
    public void generate2StageWOMAN(File file, BowType bowType, List<QualificationRound> sportsmanWOMANListInBowType) throws IOException {
        List<QualificationRound> lidersSportsmanList = new ArrayList<>(sportsmanWOMANListInBowType.subList(0, 8));
        String excelNameList = "";
        if (Objects.equals(bowType.getId(), BL_3D)) {
            excelNameList = "1,2финала 3Д_БЛ_Жен";
        } else if (Objects.equals(bowType.getId(), CL_3D)) {
            excelNameList = "1,2финала 3Д_КЛ_Жен";
        } else if (Objects.equals(bowType.getId(), LongBow_3D)) {
            excelNameList = "1,2финала 3Д-Long_Жен";
        } else if (Objects.equals(bowType.getId(), CompositeBow_3D)) {
            excelNameList = "1,2финала 3Д-Составной_Жен";
        } else if (Objects.equals(bowType.getId(), Sporting)) {
            excelNameList = "1,2финала 3Д-Sporting_Жен";
        } else if (Objects.equals(bowType.getId(), HistoryBow)) {
            excelNameList = "1,2финала 3Д-Исторический_Жен";
        } else if (Objects.equals(bowType.getId(), Olympic)) {
            excelNameList = "1,2финала 3Д-Олимпик_Жен";
        } else if (Objects.equals(bowType.getId(), Arbalet)) {
            excelNameList = "1,2финала 3Д-Арбалет_Жен";
        }

        //Значение 4 четко под формат Pattern.xlsx!!!!!!
        int rowNum = 4;

        Map<Integer, Object[]> data = prepareData(rowNum, lidersSportsmanList);

        writeDataToExcel(excelNameList, rowNum, data, file);
    }


    /**
     * Метод для вставки данных (data) в определенный лист (excelNameList) Excel файла (file)
     * Вставка данных начинается со строки rowNum + 1
     */
    private void writeDataToExcel(String excelNameList, int rowNum, Map<Integer, Object[]> data, File file) throws IOException {
        if (!data.isEmpty() && file != null) {

            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheet = workbook.getSheet(excelNameList);

            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            makeFontAndStyle(workbook, style, font);

            Set<Integer> keySet = data.keySet();

            Integer indexForCountSportsman = 1;

            for (Integer key : keySet) {
                XSSFRow row = sheet.createRow(rowNum++);
                Object[] objArr = data.get(key);
                int cellNum = 0;
                Cell firstCellForNum = row.createCell(cellNum);
                firstCellForNum.setCellValue(indexForCountSportsman.toString());
                firstCellForNum.setCellStyle(style);
                for (Object obj : objArr) {
                    Cell cell = row.createCell(++cellNum);
                    if (obj instanceof String) {
                        cell.setCellValue((String) obj);
                        cell.setCellStyle(style);
                    } else if (obj instanceof Integer) {
                        cell.setCellValue(obj.toString());
                        cell.setCellStyle(style);
                    } else if (obj instanceof Date) {
                        cell.setCellValue((Date) obj);
                        cell.setCellStyle(style);
                    } else {
                        cell.setCellValue((String) obj);
                        cell.setCellStyle(style);
                    }
                }
                indexForCountSportsman++;
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);
            sheet.autoSizeColumn(7);
            try {
                FileOutputStream out = new FileOutputStream(file);
                workbook.write(out);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
