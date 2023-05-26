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
        DataFormat format = workbook.createDataFormat();
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setDataFormat(format.getFormat("yyyy"));

        sheet.autoSizeColumn(1);
        //////////////////

        //Значение 4 четко под формат TestPattern.xlsx
        int rowNum = 4;

        Map<Integer, Object[]> data = prepareData(rowNum, applications);

        Set<Integer> keySet = data.keySet();

        for(Integer key: keySet) {
            Row row = sheet.getRow(rowNum++);
            Object[] objArr = data.get(key);
            int cellNum = 1;
            for(Object obj: objArr) {
                Cell cell = row.createCell(cellNum++);
                if (obj instanceof String) {
                    cell.setCellValue((String) obj);
                } else if (obj instanceof Integer) {
                    cell.setCellValue((Integer) obj);
                } else if (obj instanceof Date) {
                    cell.setCellStyle(dataStyle);
                    cell.setCellValue((Date) obj);
                } else {
                    cell.setCellValue((String) obj);
                }
            }
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
