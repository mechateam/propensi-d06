package propensi.d06.sihedes.controller;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import propensi.d06.sihedes.model.ProblemModel;
import propensi.d06.sihedes.model.RequestModel;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class RequestExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFSheet sheetProblems;
    private List<RequestModel> listRequests;
    private List<ProblemModel> listProblems;

    public RequestExcelExporter(List<RequestModel> listRequests, List<ProblemModel> listProblems) {
        this.listRequests = listRequests;
        this.listProblems = listProblems;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Request");
        sheetProblems = workbook.createSheet("Problem");

        Row row = sheet.createRow(0);
        Row rowProb = sheetProblems.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "No Ticket", style);
        createCell(row, 1, "Created Date", style);
        createCell(row, 2, "Username", style);
        createCell(row, 3, "Departement", style);
        createCell(row, 4, "Category", style);
        createCell(row, 5, "Description", style);
        createCell(row, 6, "SLA", style);
        createCell(row, 7, "Status", style);

        createCell(rowProb,0,"No Ticket",style);
        createCell(rowProb,1,"Created Date",style);
        createCell(rowProb, 2, "Username", style);
        createCell(rowProb, 3, "Departement", style);
        createCell(rowProb, 4, "Subject", style);
        createCell(rowProb, 5, "Description", style);
        createCell(rowProb, 6, "Status", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        sheetProblems.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Date){
            cell.setCellValue((Date) value);
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

        for (RequestModel request : listRequests) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, request.getCodeRequest(), style);
            createCell(row, columnCount++, request.getCreatedDate().toString(), style);
            createCell(row, columnCount++, request.getPengaju().getUsername(), style);
            createCell(row, columnCount++, request.getSla().getDepartemen().getNama_departemen(), style);
            createCell(row, columnCount++, request.getSla().getNama_sla(), style);
            createCell(row, columnCount++, request.getDescription(), style);
            createCell(row, columnCount++, request.getSla().getCompletion_time(), style);
            createCell(row, columnCount++, request.getStatus().getNamaStatus(), style);

        }

        rowCount = 1;
        for (ProblemModel problem: listProblems){
            Row rowProb = sheetProblems.createRow(rowCount++);
            int columnCount = 0;

            createCell(rowProb,columnCount++,problem.getCodeProblem(),style);
            createCell(rowProb,columnCount++,problem.getCreatedDate().toString(),style);
            createCell(rowProb,columnCount++,problem.getPengaju().getUsername(),style);
            createCell(rowProb,columnCount++,problem.getPengaju().getDepartemen().getNama_departemen(),style);
            createCell(rowProb,columnCount++,problem.getSubject(),style);
            createCell(rowProb,columnCount++,problem.getDescription(),style);
            createCell(rowProb,columnCount++,problem.getStatus().getNamaStatus(),style);
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

