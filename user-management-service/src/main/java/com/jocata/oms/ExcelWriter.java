package com.jocata.oms;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriter {
    public static void main(String[] args) {
        // Define the file path
        String filePath = "C:/Users/ravia/Downloads/data2.xlsx";

        // Create a workbook and sheet
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fileOut = new FileOutputStream(filePath)) {

            Sheet sheet = workbook.createSheet("Anime Characters");

            // Create the header row
            Row headerRow = sheet.createRow(0);
            String[] columns = {
                    "Full Name", "Email", "Password Hash", "Phone",
                    "Profile Picture", "OTP Secret", "SMS Enabled",
                    "Is Active", "Address", "City", "State",
                    "Country", "Zip Code", "Role",
                    "Permission", "Refresh Token", "Expires At"
            };

            // Set headers
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Dummy anime data (rows)
            String[][] animeData = {
                    {"Naruto Uzumaki", "naruto@konoha.com", "hash123", "1234567890", "naruto.jpg", "otp123", "true", "true", "Hidden Leaf", "Konoha", "Fire", "Japan", "110001", "Hokage", "Shadow Clone Jutsu", "token123", "2025-03-06T12:00:00"},
                    {"Monkey D. Luffy", "luffy@strawhat.com", "hash456", "0987654321", "luffy.jpg", "otp456", "true", "true", "Thousand Sunny", "East Blue", "Sea", "Grand Line", "200002", "Captain", "Gum Gum Pistol", "token456", "2025-03-06T14:00:00"},
                    {"Goku", "goku@earth.com", "hash789", "5678901234", "goku.jpg", "otp789", "true", "true", "Mount Paozu", "West City", "Earth", "Universe 7", "300003", "Saiyan", "Kamehameha", "token789", "2025-03-06T16:00:00"}
            };

            // Add rows with dummy data
            for (int i = 0; i < animeData.length; i++) {
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < animeData[i].length; j++) {
                    row.createCell(j).setCellValue(animeData[i][j]);
                }
            }

            workbook.write(fileOut);
            System.out.println("Excel file created successfully: " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
