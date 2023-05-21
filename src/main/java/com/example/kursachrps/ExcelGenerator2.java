package com.example.kursachrps;

import com.example.kursachrps.Models.Sportsman;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExcelGenerator2 {

    private static Map<Integer, Object[]> prepareData(int rowNum, List<Sportsman> sportsmen) {
        Map<Integer, Object[]> data = new HashMap<>();
        for(Sportsman sportsman: sportsmen) {
            rowNum++;
            data.put(rowNum, new Object[]{sportsman.getSurname() + " " + sportsman.getFirstName() + " " + sportsman.getPatronymic(),
                                            sportsman.getSex().getName(), sportsman.getRegion().getName(), sportsman.getSportsTitle().getName()});
        }
        return data;
    }


    //Функция для записи строк в excel
    public void appendRows(List<Sportsman> users, File file) throws IOException, InvalidFormatException {
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        Sheet sheet = workbook.getSheetAt(0);
        //Значение 4 четко под формат TestPattern.xlsx
        int rowNum = 4;

        Map<Integer, Object[]> data = prepareData(rowNum, users);

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
