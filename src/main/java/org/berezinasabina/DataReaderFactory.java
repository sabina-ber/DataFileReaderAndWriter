package org.berezinasabina;

public class DataReaderFactory {
    public static DataReader getDataReader(String filePath) {
        if (filePath.endsWith(".csv")) {
            return new CsvDataReader();
        } else if (filePath.endsWith(".json")) {
            return new JsonDataReader();
        } else if (filePath.endsWith(".xml")) {
            return new XmlDataReader();
        } else {
            throw new IllegalArgumentException("Unsupported file format for file: " + filePath);
        }
    }
}

