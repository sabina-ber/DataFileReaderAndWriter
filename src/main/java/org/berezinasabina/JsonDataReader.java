package org.berezinasabina;

import java.nio.file.Files;
import java.nio.file.Paths;
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
        List<Client> clients = objectMapper.readValue(jsonData, new TypeReference<List<Client>>() {
        });
        for (Client client : clients) {
            String dateString = String.valueOf(client.getBirthDate());
            LocalDate parsedDate = DateUtil.parseDate(dateString);
            client.setBirthDate(parsedDate);
        }
        return clients;
    }
}
