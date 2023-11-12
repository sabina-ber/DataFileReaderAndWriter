package org.berezinasabina;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class JsonDataReaderTest {

    @TempDir
    Path tempDir;
    JsonDataReader reader;

    @BeforeEach
    void setUp() {
        reader = new JsonDataReader();
    }

    @Test
    void testReadJsonWithValidData() throws IOException {
        Path file = tempDir.resolve("validData.json");
        Files.write(file, Arrays.asList("[{\"firstName\":\"John\",\"lastName\":\"Doe\",\"gender\":\"M\",\"birthDate\":\"1990-01-01\"}]"));

        List<Client> clients = reader.readData(file.toString());

        assertEquals(1, clients.size());
        Client client = clients.get(0);
        assertEquals("John", client.getFirstName());
        assertEquals("Doe", client.getLastName());
        assertEquals("M", client.getGender());
    }

    @Test
    void testReadJsonWithInvalidGender() throws IOException {
        Path file = tempDir.resolve("invalidGender.json");
        Files.write(file, Arrays.asList("[{\"firstName\":\"John\",\"lastName\":\"Doe\",\"gender\":\"X\",\"birthDate\":\"1990-01-01\"}]"));

        Exception exception = assertThrows(IOException.class, () -> {
            reader.readData(file.toString());
        });

        String expectedMessage = "Invalid gender found in JSON file";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void testReadJsonWithIncorrectDateFormat() {
        Path file;
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            file = Paths.get(Objects.requireNonNull(classLoader.getResource("clients_wrong_dates.json")).toURI());
        } catch (URISyntaxException e) {
            fail("Error getting resource file", e);
            return;
        }

        JsonDataReader reader = new JsonDataReader();

        assertThrows(IOException.class, () -> {
            reader.readData(file.toString());
        });
    }



}


