package lm.shortener.dao;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvConnector {

    private static final char CSV_DELIMITER = ',';

    private String csvFile;

    public CsvConnector(String csvFile) {
        this.csvFile = csvFile;
    }

    public void createRow(String[] newRow) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(csvFile, true), CSV_DELIMITER);
        List<String[]> row = new ArrayList<>();
        row.add(newRow);
        writer.writeAll(row);
        writer.flush();
        writer.close();
    }

    public String[] readRow(String key, int keyColumnIndex) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(csvFile), CSV_DELIMITER);
        String[] row;
        while ((row = reader.readNext()) != null) {
            if (key.trim().equals(row[keyColumnIndex])) {
                reader.close();
                return row;
            }
        }
        reader.close();
        return null;
    }

    public List<String[]> readRows() throws IOException {
        CSVReader reader = new CSVReader(new FileReader(csvFile), CSV_DELIMITER);
        List<String[]> rows = reader.readAll();
        reader.close();
        return rows;
    }

    public boolean updateRow(String key, String[] newRow) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(csvFile), CSV_DELIMITER);
        List<String[]> csvBody = reader.readAll();
        for (String[] aCsvBody : csvBody) {
            if (key.trim().equals(aCsvBody[0])) {
                if (aCsvBody.length == newRow.length) {
                    System.arraycopy(newRow, 0, aCsvBody, 0, aCsvBody.length);
                    break;
                } else {
                    return false;
                }
            }
        }
        reader.close();
        CSVWriter writer = new CSVWriter(new FileWriter(csvFile, false), CSV_DELIMITER);
        writer.writeAll(csvBody);
        writer.flush();
        writer.close();
        return true;
    }

    public boolean deleteRow(String key) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(csvFile), CSV_DELIMITER);
        boolean success = false;
        List<String[]> csvBody = reader.readAll();
        for (int i = 0; i < csvBody.size(); i++) {
            if (key.trim().equals(csvBody.get(i)[0])) {
                csvBody.remove(i);
                success = true;
                break;
            }
        }
        reader.close();
        CSVWriter writer = new CSVWriter(new FileWriter(csvFile, false), CSV_DELIMITER);
        writer.writeAll(csvBody);
        writer.flush();
        writer.close();
        return success;
    }

}
