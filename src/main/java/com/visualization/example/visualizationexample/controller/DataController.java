package com.visualization.example.visualizationexample.controller;

import com.visualization.example.visualizationexample.model.DataPoint;
import org.apache.poi.ss.usermodel.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class DataController {
    @GetMapping("/getData")
    public List<DataPoint> getData(@RequestParam("fileName") String fileName) {
        List<DataPoint> dataPoints = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(new File(ResourceUtils.getFile(fileName).getAbsolutePath()))) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Cell xCell = row.getCell(0);
                Cell yCell = row.getCell(1);

                if (xCell.getCellType() == CellType.NUMERIC && yCell.getCellType() == CellType.NUMERIC) {
                    dataPoints.add(new DataPoint(xCell.getNumericCellValue(), yCell.getNumericCellValue()));
                }
            }
        }
        catch (IOException e) {

            // Handle exceptions
        }

        return dataPoints;
    }

    @GetMapping(value = "/getDataAsCsv", produces = "text/csv")
    public ResponseEntity getDataAsCsv(@RequestParam("fileName") String fileName) {
        List<DataPoint> dataPoints = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(new File(ResourceUtils.getFile(fileName).getAbsolutePath()));
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            PrintWriter printWriter = new PrintWriter(outputStreamWriter, true);

            String header = "x,y";
            printWriter.println(header);

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                double x = row.getCell(0).getNumericCellValue();
                double y = row.getCell(1).getNumericCellValue();

                printWriter.println(x + "," + y);
            }
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=csvData.csv")
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .body(outputStream.toByteArray());
        }
        catch (IOException e) {
            // Handle exceptions
        }

        return null;
    }
}
