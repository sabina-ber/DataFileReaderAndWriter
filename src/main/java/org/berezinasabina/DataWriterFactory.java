package org.berezinasabina;

public class DataWriterFactory {
    public static DataWriter getDataWriter(String filePath) {
        if (filePath.endsWith(".csv")) {
            return new CsvDataWriter();
        } else if (filePath.endsWith(".json")) {
            return new JsonDataWriter();
        } else if (filePath.endsWith(".xml")) {
            return new XmlDataWriter();
        } else {
            throw new IllegalArgumentException("Unsupported file format for file: " + filePath);
        }
    }
}

