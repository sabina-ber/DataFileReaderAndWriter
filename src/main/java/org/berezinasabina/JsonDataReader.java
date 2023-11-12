package org.berezinasabina;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.time.LocalDate;

public class JsonDataReader implements DataReader {

    @Override
    public List<Client> readData(String filePath) throws IOException {
        byte[] jsonData = Files.readAllBytes(Paths.get(filePath));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<Client> clients;
        try {
            clients = objectMapper.readValue(jsonData, new TypeReference<List<Client>>() {});
        } catch (IOException e) {
            throw new IOException("Error reading JSON file " + filePath, e);
        }

        for (Client client : clients) {
            validateGender(client.getGender(), filePath);
            String dateString = String.valueOf(client.getBirthDate());
            if (dateString == null || dateString.isEmpty()) {
                throw new IOException("Error parsing date in JSON file " + filePath);
            }
            LocalDate parsedDate;
            try {
                parsedDate = DateUtil.parseDate(dateString);
            } catch (DateTimeParseException e) {
                throw new IOException("Error parsing date in JSON file ", e);
            }

            client.setBirthDate(parsedDate);
        }
        return clients;
    }

    private void validateGender (String gender, String filePath) throws IOException {
        if (!"M".equalsIgnoreCase(gender) && !"F".equalsIgnoreCase(gender)) {
            throw new IOException("Invalid gender found in JSON file " + filePath);
        }
    }
}

