package com.example.examplecrm.exporters;

import com.example.examplecrm.models.Deal;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DealExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Deal> listDeals;

    public DealExcelExporter(List<Deal> listDeals) {
        this.listDeals = listDeals;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Deals");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Deal ID", style);
        createCell(row, 1, "Client name", style);
        createCell(row, 2, "Phone", style);
        createCell(row, 3, "Price with discount", style);
        createCell(row, 4, "Product name", style);
        createCell(row, 5, "Status", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Deal Deal : listDeals) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, Deal.getId().toString(), style);
            createCell(row, columnCount++, Deal.getClient().getFullName(), style);
            createCell(row, columnCount++, Deal.getClient().getPhone(), style);
            Double priceWithDiscount = Deal.getProduct().getPrice() * ( 1 - ((double)Deal.getClient().getDiscount() / 100));
            createCell(row, columnCount++, priceWithDiscount.toString(), style);
            createCell(row, columnCount++, Deal.getProduct().getName(), style);
            createCell(row, columnCount++, Deal.getStatus(), style);

        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
