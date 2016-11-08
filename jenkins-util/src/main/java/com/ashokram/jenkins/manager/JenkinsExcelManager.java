package com.ashokram.jenkins.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ashokram.jenkins.constants.JenkinsStaticStore;
import com.ashokram.jenkins.pojo.ExcelTO;

public class JenkinsExcelManager {

    private static final Log log = LogFactory.getLog(JenkinsExcelManager.class);

    private HttpServletRequest request;

    public JenkinsExcelManager(HttpServletRequest request) {
        this.request = request;
    }

    public void writeExcelFile() throws Exception {
        writeExcelFileInGivenPath(ConfigurationManager.getConf().getConfigAsString("outputDir"));
    }

    public String writeExcelFileInGivenPath(String filePath) throws Exception {
        if (null == JenkinsStaticStore.RESULT_MAP && JenkinsStaticStore.RESULT_MAP.size() < 0) {
            return null;
        }
        String fileName = "JenkinsResult-" + System.currentTimeMillis() + ".xlsx";
        String file = filePath + File.pathSeparator + fileName;
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook();
            prepareExcelWorkbook(workbook);
            // Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(file));
            workbook.write(out);
            out.close();
            log.info(fileName + " written successfully");
        } catch (Exception e) {
            log.error("Error in creatig the Excel. ", e);
            throw e;
        } finally {
            if (null != workbook) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    log.error("Error in closing workbook.", e);
                }
            }
        }
        return file;
    }

    public ExcelTO getExcelTO() throws Exception {
        ExcelTO excelTO = new ExcelTO();
        String path = this.request.getServletContext().getRealPath("WEB-INF/../");
        path = writeExcelFileInGivenPath(path);
        InputStream inputStream = new FileInputStream(new File(path));
        excelTO.setFileName(FilenameUtils.getName(path).replaceAll(":", StringUtils.EMPTY));
        excelTO.setFullName(path);
        excelTO.setFileInputStream(inputStream);
        return excelTO;
    }

    @SuppressWarnings("unchecked")
    private void prepareExcelWorkbook(XSSFWorkbook workbook) {
        for (Entry<String, Object> jenkindDetails : JenkinsStaticStore.RESULT_MAP.entrySet()) {
            XSSFSheet spreadsheet = workbook.createSheet(jenkindDetails.getKey());
            if (jenkindDetails.getValue() instanceof Set) {
                Set<String> testCases = (Set<String>) jenkindDetails.getValue();
                int rowId = 1;
                XSSFRow row;
                Cell cell, cell2 = null;

                row = spreadsheet.createRow(0);
                cell = row.createCell(0);
                cell.setCellValue("S. No");

                cell2 = row.createCell(1);
                cell2.setCellValue("Testcase Name");
                for (String testCase : testCases) {
                    row = spreadsheet.createRow(rowId++);
                    cell = row.createCell(0);
                    cell.setCellValue(String.valueOf(rowId - 1));

                    cell2 = row.createCell(1);
                    cell2.setCellValue(testCase);
                }
            } else if (jenkindDetails.getValue() instanceof Map) {
                Map<String, Set<String>> dataMap = (Map<String, Set<String>>) jenkindDetails.getValue();
                int rowId = 0;
                XSSFRow row;
                Cell cell, cell2, cell3 = null;

                row = spreadsheet.createRow(rowId++);
                cell = row.createCell(0);
                cell.setCellValue("S. No");

                cell2 = row.createCell(1);
                cell2.setCellValue("Testcase Name");

                cell3 = row.createCell(2);
                cell3.setCellValue("Reason");
                for (Entry<String, Set<String>> jenkinsDetails : dataMap.entrySet()) {
                    Set<String> testCases = jenkinsDetails.getValue();
                    for (String testCase : testCases) {
                        row = spreadsheet.createRow(rowId++);
                        cell = row.createCell(0);
                        cell.setCellValue(String.valueOf(rowId - 1));

                        cell2 = row.createCell(1);
                        cell2.setCellValue(testCase);

                        cell3 = row.createCell(2);
                        cell3.setCellValue(jenkinsDetails.getKey());
                    }

                }
            }
        }
    }
}
