package lm.shortener.dao;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvConnector {

    private String csvFile;

    public CsvConnector(String csvFile) {
        this.csvFile = csvFile;
    }

    public void createRow(String[] newRow) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(csvFile, true), ',');
        List<String[]> row = new ArrayList<String[]>();
        row.add(newRow);
        writer.writeAll(row);
        writer.flush();
    }

    public String[] readRow(String key, int keyColumnIndex) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(csvFile), ',');
        String[] row;
        while ((row = reader.readNext()) != null) {
            if (key.trim().equals(row[keyColumnIndex])) {
                return row;
            }
        }
        return null;
    }

    public boolean updateRow(String key, String[] newRow) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(csvFile), ',');
        CSVWriter writer = new CSVWriter(new FileWriter(csvFile), ',');
        List<String[]> csvBody = reader.readAll();
        for (String[] row : csvBody) {
            if (key.trim().equals(row[0])) {
                if (row.length == newRow.length) {
                    System.arraycopy(newRow, 0, row, 0, row.length);
                    break;
                } else {
                    return false;
                }
            }
        }
        writer.writeAll(csvBody);
        writer.flush();
        return true;
    }

    public boolean deleteRow(String key) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(csvFile), ',');
        CSVWriter writer = new CSVWriter(new FileWriter(csvFile), ',');
        boolean success = false;
        List<String[]> csvBody = reader.readAll();
        for (int i = 0; i < csvBody.size(); i++) {
            if (key.trim().equals(csvBody.get(i)[0])) {
                csvBody.remove(i);
                success = true;
                break;
            }
        }
        writer.writeAll(csvBody);
        writer.flush();
        return success;
    }

}
