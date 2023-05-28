package com.example.kursachrps;

import com.example.kursachrps.Models.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ExcelGenerator2 {

    private static Map<Integer, Object[]> prepareData(int rowNum, List<Application> applications) {
        Map<Integer, Object[]> data = new HashMap<>();
        for (Application application: applications) {
            rowNum++;
            if (application.getCoach() == null) {
                Sportsman sportsman = application.getSportsman();
                Sex sexName = sportsman.getSex();
                SportsTitle sportsTitle = sportsman.getSportsTitle();
                Region region = sportsman.getRegion();
                BowType bowType = application.getBowType();
                if (sexName != null && sportsTitle != null && region != null) {
                    data.put(rowNum, new Object[]{sportsman.getSurname() + " " + sportsman.getFirstName() + " " + sportsman.getPatronymic(),
                            sexName.getName(), sportsman.getBirthDate(), region.getName(), sportsTitle.getName(), bowType.getBowTypeName()});
                }
            } else if(application.getSportsman() == null) {
                Coach coach = application.getCoach();
                Sex sexName = coach.getSex();
                SportsTitle sportsTitle = coach.getSportsTitle();
                Region region = coach.getRegion();
                BowType bowType = application.getBowType();
                if (sexName != null && sportsTitle != null && region != null) {
                    data.put(rowNum, new Object[]{coach.getSurname() + " " + coach.getFirstName() + " " + coach.getPatronymic(),
                            sexName.getName(), coach.getBirthDate(), region.getName(), sportsTitle.getName(), bowType.getBowTypeName()});
                }
            }
        }
        return data;
    }

    //Функция для записи строк в excel
    public void appendRows(List<Application> applications, File file) throws IOException, InvalidFormatException {
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        Sheet sheet = workbook.getSheetAt(0);

        //////////////////
        CellStyle style = workbook.createCellStyle();
        //Стиль для шрифта
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFont(font);
        //Стиль для даты
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("yyyy"));
        //Стиль для рамки вокруг ячеек
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        //////////////////

        //Значение 4 четко под формат TestPattern.xlsx
        int rowNum = 4;

        Map<Integer, Object[]> data = prepareData(rowNum, applications);

        Set<Integer> keySet = data.keySet();

        for(Integer key: keySet) {
            Row row = sheet.getRow(rowNum++);
            Object[] objArr = data.get(key);
            int cellNum = 0;
            for(Object obj: objArr) {
                Cell cell = row.createCell(cellNum++);
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
}
