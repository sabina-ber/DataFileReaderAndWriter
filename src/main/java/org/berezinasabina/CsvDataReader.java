package org.berezinasabina;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.Reader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CsvDataReader implements DataReader {

    public List<Client> readData(String filePath) {
        List<Client> clients = new ArrayList<>();
        try (
                Reader in = new InputStreamReader(Files.newInputStream(Paths.get(filePath)), StandardCharsets.UTF_8);
                CSVParser parser = new CSVParser(in, CSVFormat.DEFAULT.withFirstRecordAsHeader())
        ) {
            for (CSVRecord record : parser) {
                String firstName = record.get("Firstname").trim();
                String lastName = record.get("Lastname").trim();
                String gender = record.get("Gender").trim();
                LocalDate birthDate = DateUtil.parseDate(record.get("Birthdate"));

                Client client = new Client(firstName, lastName, gender, birthDate);
                clients.add(client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clients;
    }
}
