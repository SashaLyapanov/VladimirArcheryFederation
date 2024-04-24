package com.example.kursachrps.service;

import com.example.kursachrps.models.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private static Map<Integer, Object[]> prepareData(int rowNum, List<Application> applications) {
        Map<Integer, Object[]> data = new HashMap<>();
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
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        XSSFSheet sheet = workbook.getSheetAt(0);

        CellStyle style = workbook.createCellStyle();
        //Стиль для шрифта
        Font font = workbook.createFont();
        makeFontAndStyle(workbook, style, font);

        //Значение 4 четко под формат Pattern.xlsx
        int rowNum = 4;

        Map<Integer, Object[]> data = prepareData(rowNum, applications);

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
                    cell.setCellValue((Integer) obj);
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
        try {
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
    public void generate8StageMAN(InputStream inputStream, BowType bowType, List<QualificationRound> sportsmanMANListInBowType) {
        /**
         * итак, у нас есть файл протокола, тип лука, сортированный список спортсменов для заноса на данный лист
         * таким образом, мы должны обрубить выборку sportsmanMANListInBowType до 16 человек
         * дальше создать лист в файле inputStream с названием 1/8 финала + bowType.getName() + мужчины
         * в него заносим шапку листа
         * далее заносим спортсменов
         */
        List<QualificationRound> lidersSportsmanList = new ArrayList<>(sportsmanMANListInBowType.subList(0, 16));
        int excelIndexList = 0;
        if (Objects.equals(bowType.getId(), BL_3D)) {
            excelIndexList = 1;
        } else if (Objects.equals(bowType.getId(), CL_3D)) {
            excelIndexList = 3;
        } else if (Objects.equals(bowType.getId(), LongBow_3D)) {
            excelIndexList = 5;
        } else if (Objects.equals(bowType.getId(), CompositeBow_3D)) {
            excelIndexList = 7;
        } else if (Objects.equals(bowType.getId(), Sporting)) {
            excelIndexList = 8;
        } else if (Objects.equals(bowType.getId(), HistoryBow)) {
            excelIndexList = 9;
        } else if (Objects.equals(bowType.getId(), Olympic)) {
            excelIndexList = 10;
        } else if (Objects.equals(bowType.getId(), Arbalet)) {
            excelIndexList =11;
        }

        /**
         * Дальше логика такая:
         * -прописываю фукнцию, которая вносит список List<QualificationRound> lidersSportsmanList в нужный нам лист Excel, индекс которого мы определили выше
         * -юзаю эту функцию. ПАРАМЕТРЫ в данной фукнции (List<QualificationRound> lidersSportsmanList, InputStream inputStream, int excelIndexList)
         * -данная фукнция должна подходить ко всем стадиям генерации протокола
         * -это значит, что шаблон нужно сделать единый для всех стадий от 1/8 до 1/2
         */
    }

    /**
     * Метод для генерации 1/4 финала у мужчин
     */
    public void generate4StageMAN(InputStream inputStream, BowType bowType, List<QualificationRound> sportsmanMANListInBowType) {

    }

    /**
     * Метод для генерации 1/2 финала у мужчин
     */
    public void generate2StageMAN(InputStream inputStream, BowType bowType, List<QualificationRound> sportsmanMANListInBowType) {

    }

    /**
     * Метод для генерации 1/8 финала у женщин
     */
    public void generate8StageWOMAN(InputStream inputStream, BowType bowType, List<QualificationRound> sportsmanMANListInBowType) {

    }

    /**
     * Метод для генерации 1/4 финала у женщин
     */
    public void generate4StageWOMAN(InputStream inputStream, BowType bowType, List<QualificationRound> sportsmanMANListInBowType) {

    }

    /**
     * Метод для генерации 1/2 финала у мужчин
     */
    public void generate2StageWOMAN(InputStream inputStream, BowType bowType, List<QualificationRound> sportsmanMANListInBowType) {

    }

}
