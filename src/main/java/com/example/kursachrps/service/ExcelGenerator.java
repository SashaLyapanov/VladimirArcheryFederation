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
}
