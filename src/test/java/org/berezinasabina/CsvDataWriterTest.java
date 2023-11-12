package org.berezinasabina;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvDataWriterTest {

    @TempDir
    Path tempDir;

    @Test
    void testWriteData() throws IOException {
        Client client1 = new Client("John", "Doe", "M", LocalDate.of(1990, 1, 1));
        Client client2 = new Client("Jane", "Doe", "F", LocalDate.of(1991, 2, 2));
        List<Client> clients = Arrays.asList(client1, client2);

        Path testFile = tempDir.resolve("testClients.csv");

        CsvDataWriter csvDataWriter = new CsvDataWriter();
        csvDataWriter.writeData(clients, testFile.toString());

        assertTrue(Files.exists(testFile));

        try (Reader reader = Files.newBufferedReader(testFile);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())
        ) {
            List<CSVRecord> records = csvParser.getRecords();
            assertEquals(2, records.size());

            CSVRecord record1 = records.get(0);
            assertEquals("John", record1.get("Firstname"));
            assertEquals("Doe", record1.get("Lastname"));
            assertEquals("M", record1.get("Gender"));
            assertEquals("1990-01-01", record1.get("Birthdate"));

            CSVRecord record2 = records.get(1);
            assertEquals("Jane", record2.get("Firstname"));
            assertEquals("Doe", record2.get("Lastname"));
            assertEquals("F", record2.get("Gender"));
            assertEquals("1991-02-02", record2.get("Birthdate"));
        }
    }
}
