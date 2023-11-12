package org.berezinasabina;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class CsvDataReader implements DataReader {

    public List<Client> readData(String filePath) throws IOException {
        List<Client> clients = new ArrayList<>();
        try (
                Reader in = new InputStreamReader(Files.newInputStream(Paths.get(filePath)), StandardCharsets.UTF_8);
                CSVParser parser = new CSVParser(in, CSVFormat.DEFAULT.withFirstRecordAsHeader())
        ) {
            for (CSVRecord record : parser) {
                String firstName = record.get("Firstname").trim();
                String lastName = record.get("Lastname").trim();
                String gender = record.get("Gender").trim();
                LocalDate birthDate;
                try {
                    birthDate = DateUtil.parseDate(record.get("Birthdate"));
                } catch (DateTimeParseException e) {
                    throw new IOException("Error parsing date in file " + filePath, e);
                }
                validateGender(gender, filePath);

                Client client = new Client(firstName, lastName, gender, birthDate);
                clients.add(client);
            }
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new IOException("Error reading CSV file " + filePath, e);
        }

        return clients;
    }

    private void validateGender (String gender, String filePath) throws IOException {
        if (!"M".equalsIgnoreCase(gender) && !"F".equalsIgnoreCase(gender)) {
            throw new IOException("Invalid gender in file " + filePath);
        }
    }
}

