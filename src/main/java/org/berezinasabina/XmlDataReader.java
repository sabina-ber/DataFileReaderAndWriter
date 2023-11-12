package org.berezinasabina;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class XmlDataReader implements DataReader {

    @Override
    public List<Client> readData(String filePath) throws IOException {
        byte[] xmlData = Files.readAllBytes(Paths.get(filePath));
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());

        List<Client> clients;
        try {
            clients = xmlMapper.readValue(xmlData, new TypeReference<List<Client>>() {});
        } catch (IOException e) {
            throw new IOException("Error reading XML file " + filePath, e);
        }

        for (Client client : clients) {
            validateGender(client.getGender(), filePath);
            LocalDate birthDate = client.getBirthDate();
            if (birthDate == null) {
                String dateString = String.valueOf(client.getBirthDate());
                LocalDate parsedDate;
                try {
                    parsedDate = DateUtil.parseDate(dateString);
                } catch (DateTimeParseException | IllegalArgumentException e) {
                    throw new IOException("Error parsing date in XML file " + filePath + ": " + e.getMessage(), e);
                }
                client.setBirthDate(parsedDate);
            }
        }
        return clients;
    }

    private void validateGender (String gender, String filePath) throws IOException {
        if (!"M".equalsIgnoreCase(gender) && !"F".equalsIgnoreCase(gender)) {
            throw new IOException("Invalid gender found in XML file " + filePath);
        }
    }
}

