package org.example.mrdverkin.reportCreators;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dto.ReportDTO;
import org.springframework.stereotype.Component;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ExcelCreater implements Creater{

    @Override
    public byte[] convertReport(ReportDTO report) {
        try (HSSFWorkbook workbook = new HSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            HSSFSheet sheet = workbook.createSheet("Лист 1");

            List<Order> dataList = report.getOrders();

            int rowNum = 0;
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue("ФИО");
            row.createCell(1).setCellValue("Адрес");
            row.createCell(2).setCellValue("Номер");
            row.createCell(3).setCellValue("Входные двери");
            row.createCell(4).setCellValue("Межкомнатные двери");
            row.createCell(5).setCellValue("Комментарий продавца");

            for (Order order : dataList) {
                createSheetHeader(sheet, rowNum++, order);
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании Excel", e);
        }
    }

    // заполнение строки (rowNum) определенного листа (sheet)
    // данными  из dataModel созданного в памяти Excel файла
    private static void createSheetHeader(HSSFSheet sheet, int rowNum, Order order) {
        Row row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(order.getFullName());
            row.createCell(1).setCellValue(order.getAddress());
            row.createCell(2).setCellValue(order.getPhone());
            row.createCell(3).setCellValue(order.getFrontDoorQuantity());
            row.createCell(4).setCellValue(order.getInDoorQuantity());
            row.createCell(5).setCellValue(order.getMessageSeller());
    }
}
