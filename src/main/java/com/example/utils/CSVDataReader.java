package com.example.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVDataReader {
    
    /**
     * Reads CSV file and returns data as Object[][]
     * @param filePath Path to the CSV file
     * @return Object[][] containing test data
     */
    public static Object[][] readCSVData(String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> records = reader.readAll();
            
            // Remove header row if present
            if (!records.isEmpty()) {
                records.remove(0);
            }
            
            // Convert to Object[][]
            Object[][] data = new Object[records.size()][];
            for (int i = 0; i < records.size(); i++) {
                data[i] = records.get(i);
            }
            
            return data;
            
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read CSV file: " + filePath, e);
        }
    }
    
    /**
     * Reads CSV file and returns specific columns as Object[][]
     * @param filePath Path to the CSV file
     * @param columnIndices Indices of columns to extract (0-based)
     * @return Object[][] containing selected columns
     */
    public static Object[][] readCSVDataWithColumns(String filePath, int... columnIndices) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> records = reader.readAll();
            
            // Remove header row if present
            if (!records.isEmpty()) {
                records.remove(0);
            }
            
            // Extract specific columns
            Object[][] data = new Object[records.size()][];
            for (int i = 0; i < records.size(); i++) {
                Object[] row = new Object[columnIndices.length];
                for (int j = 0; j < columnIndices.length; j++) {
                    if (columnIndices[j] < records.get(i).length) {
                        row[j] = records.get(i)[columnIndices[j]];
                    } else {
                        row[j] = "";
                    }
                }
                data[i] = row;
            }
            
            return data;
            
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read CSV file: " + filePath, e);
        }
    }
    
    /**
     * Prints CSV data for debugging
     * @param data Object[][] data to print
     */
    public static void printData(Object[][] data) {
        System.out.println("CSV Data:");
        for (int i = 0; i < data.length; i++) {
            System.out.print("Row " + (i + 1) + ": ");
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j] + " | ");
            }
            System.out.println();
        }
    }
}
