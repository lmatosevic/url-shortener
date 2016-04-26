package lm.shortener.dao;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvConnector {

    private CSVReader reader;
    private CSVWriter writer;
    private CSVWriter writerAppend;

    public CsvConnector(String csvFile) {
        try {
            reader = new CSVReader(new FileReader(csvFile), ',');
            writer = new CSVWriter(new FileWriter(csvFile), ',');
            writerAppend = new CSVWriter(new FileWriter(csvFile, true), ',');
        } catch (Exception e) {
            reader = null;
            writer = null;
        }
    }

    public void createRow(String[] newRow) throws IOException {
        List<String[]> row = new ArrayList<String[]>();
        row.add(newRow);
        writerAppend.writeAll(row);
    }

    public String[] readRow(String key) throws IOException {
        String[] row;
        while ((row = reader.readNext()) != null) {
            if (key.trim().equals(row[0])) {
                return row;
            }
        }
        return null;
    }

    public boolean updateRow(String key, String[] newRow) throws IOException {
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
