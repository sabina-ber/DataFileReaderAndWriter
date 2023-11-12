package org.berezinasabina;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonDataWriterTest {

    @TempDir
    Path tempDir;

    @Test
    void testWriteData() throws IOException {
        Client client1 = new Client("John", "Doe", "M", LocalDate.of(1990, 1, 1));
        Client client2 = new Client("Jane", "Doe", "F", LocalDate.of(1991, 2, 2));
        List<Client> clients = Arrays.asList(client1, client2);

        Path testFile = tempDir.resolve("testClients.json");

        JsonDataWriter jsonDataWriter = new JsonDataWriter();
        jsonDataWriter.writeData(clients, testFile.toString());

        assertTrue(Files.exists(testFile));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<Client> readClients = objectMapper.readValue(testFile.toFile(), objectMapper.getTypeFactory().constructCollectionType(List.class, Client.class));

        assertNotNull(readClients);
        assertEquals(2, readClients.size());
        assertEquals(client1, readClients.get(0));
        assertEquals(client2, readClients.get(1));
    }
}
