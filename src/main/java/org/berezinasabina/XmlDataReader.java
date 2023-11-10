package org.berezinasabina;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class XmlDataReader implements DataReader {

    @Override
    public List<Client> readData(String filePath) throws IOException {
        byte[] xmlData = Files.readAllBytes(Paths.get(filePath));
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());

        List<Client> clients = xmlMapper.readValue(xmlData, new TypeReference<List<Client>>() {});

        for (Client client : clients) {
            LocalDate birthDate = client.getBirthDate();
            if (birthDate == null) {
                String dateString = String.valueOf(client.getBirthDate());
                LocalDate parsedDate = parseDate(dateString);
                client.setBirthDate(parsedDate);
            }
        }
        return clients;
    }

    private LocalDate parseDate(String dateString) {
        DateTimeFormatter[] formatters = new DateTimeFormatter[] {
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("yyyy-dd-MM"),
                DateTimeFormatter.ofPattern("yyyy.MM.dd"),
                DateTimeFormatter.ofPattern("yyyy.dd.MM"),
                DateTimeFormatter.ofPattern("dd.MM.yyyy"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                DateTimeFormatter.ofPattern("dd MM yyyy")
        };

        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(dateString.trim(), formatter);
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new DateTimeParseException("Could not parse the date: " + dateString, dateString, 0);
    }
}
