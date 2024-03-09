package Pages;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelDataReader {

    public static void main(String[] args) {
        String excelFilePath = "Data Sheet.xlsx";

        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Retrieve data from the student sheet
            Sheet studentSheet = workbook.getSheet("Student");
         isValidStudentSheet(studentSheet);
                retrieveStudentData(studentSheet);

            // Retrieve data from the school sheet
            Sheet schoolSheet = workbook.getSheet("School");
           isValidSchoolSheet(schoolSheet);
                retrieveSchoolData(schoolSheet);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValidStudentSheet(Sheet studentSheet) {
        // Check if the sheet is not null and has the expected headers
        if (studentSheet != null) {
            Row headerRow = studentSheet.getRow(0);
            if (headerRow != null) {
                String[] expectedHeaders = {"National ID", "English First Name", "English Family Name", "English Third Name",
                        "English Fourth Name", "Birth Country", "Nationality", "Gender", "Religion", "Nationality Category", "School ID"};
                for (int i = 0; i < expectedHeaders.length; i++) {
                    Cell cell = headerRow.getCell(i);
                    if (cell == null || !cell.getStringCellValue().equals(expectedHeaders[i])) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private static boolean isValidSchoolSheet(Sheet schoolSheet) {
        // Check if the sheet is not null and has the expected headers
        if (schoolSheet != null) {
            Row headerRow = schoolSheet.getRow(0);
            if (headerRow != null) {
                String[] expectedHeaders = {"School ID", "School Name", "Gender", "Religion"};
                for (int i = 0; i < expectedHeaders.length; i++) {
                    Cell cell = headerRow.getCell(i);
                    if (cell == null || !cell.getStringCellValue().equals(expectedHeaders[i])) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private static void retrieveStudentData(Sheet studentSheet) {
        for (Row row : studentSheet) {
            // Assuming the columns are in the order specified in the question
            String nationalID = getStringValue(row.getCell(0));
            String gender = getStringValue(row.getCell(7));
            String firstName = getStringValue(row.getCell(1));
            String familyName = getStringValue(row.getCell(2));
            String thirdName = getStringValue(row.getCell(3));
            String fourthName = getStringValue(row.getCell(3));


            // Check National ID and Gender conditions
            if (nationalID != null && nationalID.startsWith("1234") && nationalID.length() == 10 && firstName != null && familyName != null && thirdName != null && fourthName != null ) {
                System.out.println("Valid data: " +" " + nationalID +" "+ firstName +" "+ familyName +" "+ thirdName +" "+ fourthName);
                System.out.println("Gender: " + gender);
            }

            if (gender != null) {
                // Assuming mixed is represented as "mixed"
                if (!gender.equalsIgnoreCase("mixed")) {
                    String schoolGender = getSchoolGender(row.getCell(10)); // Assuming School ID is at index 10
                    if (gender.equalsIgnoreCase(schoolGender)) {
                        System.out.println("Valid Gender: " + gender);
                    }
                }
            }
        }
    }

    private static void retrieveSchoolData(Sheet schoolSheet) {
        for (Row row : schoolSheet) {
            // Assuming the columns are in the order specified in the question
            String schoolID = getStringValue(row.getCell(0));
            String schoolGender = getStringValue(row.getCell(2));

            // Process school data as needed
            System.out.println("School ID: " + schoolID + ", Gender: " + schoolGender);
        }
    }

    private static String getStringValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue();
    }

    private static String getSchoolGender(Cell cell) {
        return getStringValue(cell);
    }
}
