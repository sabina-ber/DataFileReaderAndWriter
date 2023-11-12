package org.berezinasabina;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.net.URL;
import java.util.List;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;

class CsvDataReaderTest {

    @TempDir
    Path tempDir;

    @Test
    void testReadEmptyCsv() throws IOException {
        Path file = tempDir.resolve("empty.csv");
        Files.write(file, "Firstname,Lastname,Gender,Birthdate\n".getBytes());

        CsvDataReader reader = new CsvDataReader();
        List<Client> clients = reader.readData(file.toString());

        assertTrue(clients.isEmpty());
    }

    @Test
    void testReadCsvWithInvalidGender() throws IOException {
        Path file = tempDir.resolve("invalidGender.csv");
        Files.write(file, "Firstname,Lastname,Gender,Birthdate\nJohn,Doe,X,1990-01-01\n".getBytes());

        CsvDataReader reader = new CsvDataReader();

        IOException exception = assertThrows(IOException.class, () -> {
            reader.readData(file.toString());
        });

        assertTrue(exception.getMessage().contains("Invalid gender"));
    }

    @Test
    void testReadCsvWithInvalidDate() throws IOException {
        Path file = tempDir.resolve("invalidDate.csv");
        Files.write(file, "Firstname,Lastname,Gender,Birthdate\nJane,Doe,F,10-31-01\n".getBytes());

        CsvDataReader reader = new CsvDataReader();

        IOException exception = assertThrows(IOException.class, () -> {
            reader.readData(file.toString());
        });

        assertTrue(exception.getMessage().contains("Error parsing date"));
    }

    @Test
    void readData_InvalidDateFormat_ThrowsIOException() throws IOException, URISyntaxException {
        URL resource = getClass().getClassLoader().getResource("clients_wrong_date.csv");
        if (resource == null) {
            fail("Test file not found.");
            return;
        }
        Path resourceFile = Paths.get(resource.toURI());

        CsvDataReader reader = new CsvDataReader();

        IOException exception = assertThrows(IOException.class, () -> {
            reader.readData(resourceFile.toString());
        });

        assertTrue(exception.getMessage().contains("Error parsing date"));
    }
}

