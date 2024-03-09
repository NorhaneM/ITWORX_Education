package Pages;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ExcelDataFilter {

    private static void processValidData(Sheet studentSheet, Sheet validSchoolSheet, Sheet validDataSheet) {
        // Add header row for validDataSheet
        Row headerRowData = studentSheet.getRow(0);
        Row targetHeaderRowData = validDataSheet.createRow(0);
        copyHeaderRow(headerRowData, targetHeaderRowData);
        Cell schoolGenderHeaderCell = targetHeaderRowData.createCell(targetHeaderRowData.getLastCellNum());
        schoolGenderHeaderCell.setCellValue("School_Gender (R)");

        // Use a set to keep track of unique rows
        Set<String> uniqueRows = new HashSet<>();

        // Iterate through rows and filter
        for (int i = 1; i <= studentSheet.getLastRowNum(); i++) {
            Row row = studentSheet.getRow(i);
            if (row != null) {
                // Check valid ID and construct a unique key
                if (validID(row.getCell(0))) {
                    String uniqueKey = getUniqueKey(row);

                    // Check if the row is not a duplicate
                    if (uniqueRows.add(uniqueKey)) {
                        // Check school gender condition
                        String schoolIdRefValue = getStringValue(row.getCell(0));
                        Row schoolRow = findSchoolRow(validSchoolSheet, schoolIdRefValue);

                        if (schoolRow != null) {
                            String schoolGenderValue = getStringValue(schoolRow.getCell(2));

                            if ("Mixed".equals(schoolGenderValue) || getStringValue(row.getCell(6)).equals(schoolGenderValue)) {
                                // Add to validDataSheet
                                Row newRow = validDataSheet.createRow(validDataSheet.getLastRowNum() + 1);
                                copyRow(row, newRow);
                                Cell schoolGenderCellData = newRow.createCell(newRow.getLastCellNum());
                                schoolGenderCellData.setCellValue(schoolGenderValue);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        try (FileInputStream fileInputStream = new FileInputStream("Data Sheet.xlsx");
             FileOutputStream validDataFileOutputStream = new FileOutputStream("Valid_data_sheet.xlsx")) {

            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet studentSheet = workbook.getSheet("Student");
            Sheet schoolSheet = workbook.getSheet("School");

            // --------------------------------- Filter School sheet ---------------------------------
            Sheet validSchoolSheet = createValidSchoolSheet(schoolSheet);

            // --------------------------------- Merge and Filter Data ---------------------------------
            Sheet validDataSheet = workbook.createSheet("ValidData");

            // Process valid data and write to validDataSheet
            processValidData(studentSheet, validSchoolSheet, validDataSheet);

            // Write the updated workbook to the output file
            workbook.write(validDataFileOutputStream);
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Sheet createValidSchoolSheet(Sheet schoolSheet) {
        Workbook workbook = schoolSheet.getWorkbook();
        Sheet validSchoolSheet = workbook.createSheet("ValidSchool");

        // Add header row
        Row headerRowSchool = schoolSheet.getRow(0);
        Row targetHeaderRowSchool = validSchoolSheet.createRow(0);
        copyHeaderRow(headerRowSchool, targetHeaderRowSchool);

        // Iterate through rows and filter
        for (int i = 1; i <= schoolSheet.getLastRowNum(); i++) {
            Row row = schoolSheet.getRow(i);
            if (row != null) {
                Cell schoolIdCell = row.getCell(0);
                Cell schoolNameCell = row.getCell(1);
                Cell schoolGenderCell = row.getCell(2);
                Cell schoolReligionCell = row.getCell(3);

                if (schoolIdCell != null && schoolNameCell != null && schoolGenderCell != null && schoolReligionCell != null) {
                    String schoolIdValue = getStringValue(schoolIdCell);
                    String schoolNameValue = getStringValue(schoolNameCell);
                    String schoolGenderValue = getStringValue(schoolGenderCell);
                    String schoolReligionValue = getStringValue(schoolReligionCell);

                    if (schoolIdValue != null && schoolNameValue != null) {
                        // Add to validSchoolSheet
                        Row newRow = validSchoolSheet.createRow(validSchoolSheet.getLastRowNum() + 1);
                        newRow.createCell(0).setCellValue(schoolIdValue);
                        newRow.createCell(1).setCellValue(schoolNameValue);
                        newRow.createCell(2).setCellValue(schoolGenderValue);
                        newRow.createCell(3).setCellValue(schoolReligionValue);
                    }
                }
            }
        }

        return validSchoolSheet;
    }

    private static void copyHeaderRow(Row sourceRow, Row targetRow) {
        // Helper method to copy header row
        for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
            Cell sourceCell = sourceRow.getCell(i);
            Cell targetCell = targetRow.createCell(i);
            if (sourceCell != null) {
                targetCell.setCellValue(sourceCell.getStringCellValue());
            }
        }
    }

    private static Row findSchoolRow(Sheet sheet, String schoolId) {
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell schoolIdCell = row.getCell(0);
                if (schoolIdCell != null && schoolId.equals(getStringValue(schoolIdCell))) {
                    return row;
                }
            }
        }
        return null;
    }

    // Helper method to copy data from sourceRow to targetRow
    private static void copyRow(Row sourceRow, Row targetRow) {
        for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
            Cell sourceCell = sourceRow.getCell(i);
            Cell targetCell = targetRow.createCell(i);

            if (sourceCell != null) {
                switch (sourceCell.getCellType()) {
                    case STRING:
                        targetCell.setCellValue(sourceCell.getStringCellValue());
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(sourceCell)) {
                            targetCell.setCellValue(sourceCell.getDateCellValue());
                        } else {
                            targetCell.setCellValue(sourceCell.getNumericCellValue());
                        }
                        break;
                    case BOOLEAN:
                        targetCell.setCellValue(sourceCell.getBooleanCellValue());
                        break;
                    case FORMULA:
                        targetCell.setCellFormula(sourceCell.getCellFormula());
                        break;
                    // Add more cases as needed for different cell types
                }
            }
        }
    }

    private static String getStringValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            default:
                return null;
        }
    }

    private static String getUniqueKey(Row row) {
        // Helper method to generate a unique key based on relevant columns
        StringBuilder keyBuilder = new StringBuilder();
        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null) {
                switch (cell.getCellType()) {
                    case STRING:
                        keyBuilder.append(cell.getStringCellValue());
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            keyBuilder.append(cell.getDateCellValue().toString());
                        } else {
                            keyBuilder.append(cell.getNumericCellValue());
                        }
                        break;
                    default:
                        // Handle other cell types as needed
                        break;
                }
            }
            keyBuilder.append("_");
        }
        return keyBuilder.toString();
    }

    // Helper function to check valid ID
    private static boolean validID(Cell cell) {
        boolean valid = false;

        switch (cell.getCellType()) {
            case STRING:
                String ID = cell.getStringCellValue();
                if (ID.startsWith("1234") && ID.length() == 10) {
                    valid = true;
                }
                break;

            case NUMERIC:
                // Handle numeric values
                String numericID = String.valueOf((long) cell.getNumericCellValue());
                if (numericID.startsWith("1234") && numericID.length() == 10) {
                    valid = true;
                }
                break;

            // Add more cases as needed for different cell types
        }

        return valid;
    }
}
