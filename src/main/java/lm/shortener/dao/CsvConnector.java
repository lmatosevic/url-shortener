package lm.shortener.dao;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used by DAO for reading and storing model objects in csv file.
 *
 * @author Luka
 */
public class CsvConnector {

    private static final char CSV_DELIMITER = ',';

    private String csvFile;

    /**
     * Constructor that initializes storage csv file path.
     *
     * @param csvFile Csv file path.
     */
    public CsvConnector(String csvFile) {
        this.csvFile = csvFile;
    }

    /**
     * Creates new row at the end of csv file from provided values.
     *
     * @param newRow Array of string which needs to be stored.
     * @throws IOException
     */
    public void createRow(String[] newRow) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(csvFile, true), CSV_DELIMITER);
        List<String[]> row = new ArrayList<>();
        row.add(newRow);
        writer.writeAll(row);
        writer.flush();
        writer.close();
    }

    /**
     * Reads csv row from file based on provided key and key column.
     *
     * @param key String value of key used for searching desired row.
     * @param keyColumnIndex Index of column in which key is positioned.
     * @return Searched row as array of strings or null if not found.
     * @throws IOException
     */
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

    /**
     * Reads all rows from csv file.
     *
     * @return All rows as list of string arrays.
     * @throws IOException
     */
    public List<String[]> readRows() throws IOException {
        CSVReader reader = new CSVReader(new FileReader(csvFile), CSV_DELIMITER);
        List<String[]> rows = reader.readAll();
        reader.close();
        return rows;
    }

    /**
     * Updates row with new values.
     *
     * @param key Identifier of row which needs to be updated.
     * @param newRow New row values.
     * @return True if row is successfuly updated, false otherwise.
     * @throws IOException
     */
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

    /**
     * Deletes one row from csv file.
     *
     * @param key Identifier of row which needs to be deleted.
     * @return True of row is successfuly delete, false otherwise.
     * @throws IOException
     */
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
