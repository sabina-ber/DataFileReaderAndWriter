package org.berezinasabina;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CsvDataWriter implements DataWriter {
    @Override
    public void writeData(List<Client> data,  String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Files.createDirectories(path.getParent());

        try (
                BufferedWriter writer = Files.newBufferedWriter(path);
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Firstname", "Lastname", "Gender", "Birthdate"))
        ) {
            for (Client client : data) {
                csvPrinter.printRecord(
                        client.getFirstName(),
                        client.getLastName(),
                        client.getGender(),
                        client.getBirthDate().toString()
                );
            }
        }
    }
}

