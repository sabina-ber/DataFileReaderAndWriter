package org.berezinasabina;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientRepositoryTest {

    private ClientRepository clientRepository;
    private List<Client> testData;

    @BeforeEach
    void setUp() {
        clientRepository = new ClientRepository();
        testData = new ArrayList<>();
        testData.add(new Client("John", "Doe", "M", LocalDate.of(1980, 1, 1)));
        testData.add(new Client("Jane", "Doe", "F", LocalDate.of(1990, 2, 2)));
        testData.add(new Client("Sarah", "Smith", "F", LocalDate.of(1985, 3, 3)));
        testData.add(new Client("Mike", "Johnson", "M", LocalDate.of(1995, 4, 4)));
        testData.add(new Client("John", "Adams", "M", LocalDate.of(2000, 5, 5)));
        testData.add(new Client("Emily", "Davis", "F", LocalDate.of(1993, 6, 6)));
        testData.add(new Client("William", "Brown", "M", LocalDate.of(1988, 7, 7)));
        testData.add(new Client("Emma", "Wilson", "F", LocalDate.of(2001, 8, 8)));
        testData.add(new Client("Oliver", "Miller", "M", LocalDate.of(1999, 9, 9)));
        testData.add(new Client("Sophia", "Taylor", "F", LocalDate.of(1992, 10, 10)));
        testData.add(new Client("James", "Anderson", "M", LocalDate.of(1982, 11, 11)));
        testData.add(new Client("Isabella", "Thomas", "F", LocalDate.of(1996, 12, 12)));
        testData.add(new Client("Lucas", "Moore", "M", LocalDate.of(1989, 8, 13)));
        testData.add(new Client("Mia", "Jackson", "F", LocalDate.of(1994, 9, 14)));
        testData.add(new Client("John", "Martin", "M", LocalDate.of(1975, 10, 15)));
        for (Client client : testData) {
            clientRepository.addClient(client);
        }
    }

    @Test
    @Tag("ExternalResource")
    void testLoadCsvDataFromResources() throws IOException, URISyntaxException {
        ClientRepository localClientRepository = new ClientRepository();

        URL resourceUrl = getClass().getClassLoader().getResource("clients_correct.csv");
        assert resourceUrl != null;
        Path resourcePath = Paths.get(resourceUrl.toURI());

        localClientRepository.loadData(resourcePath.toString());

        List<Client> clients = localClientRepository.getAllClients();
        short expectedNumberOfClients = 107;
        assertEquals(expectedNumberOfClients, clients.size());
    }

    @Test
    void testFilterByGender() {
        FilterConfig filterConfig = new FilterConfig();
        filterConfig.setMalesOnly(true);

        List<Client> filteredClients = clientRepository.getFilteredClients(filterConfig);
        filteredClients.forEach(client -> assertEquals("M", client.getGender()));
    }

    @Test
    void testFilterByLastNameAndGender() {
        FilterConfig filterConfig = new FilterConfig();
        filterConfig.setLastNameFilter("Doe");
        filterConfig.setFemalesOnly(true);

        List<Client> filteredClients = clientRepository.getFilteredClients(filterConfig);
        filteredClients.forEach(client -> {
            assertEquals("Doe", client.getLastName());
            assertEquals("F", client.getGender());
        });
    }

    @Test
    void testGetTopFiveClients() {
        List<Client> sortedTestData = new ArrayList<>(testData);
        sortedTestData.sort(
                Comparator.comparing(Client::getBirthDate)
                        .thenComparing(Client::getLastName)
                        .thenComparing(Client::getFirstName)
        );

        FilterConfig filterConfig = new FilterConfig();
        filterConfig.setTopRecords(5);

        List<Client> topClients = clientRepository.getFilteredClients(filterConfig);

        assertEquals(5, topClients.size());
        for (int i = 0; i < 5; i++) {
            assertEquals(sortedTestData.get(i), topClients.get(i));
        }
    }

    @Test
    void testGetLastFiveClients() {
        List<Client> sortedTestData = new ArrayList<>(testData);
        sortedTestData.sort(
                Comparator.comparing(Client::getBirthDate)
                        .thenComparing(Client::getLastName)
                        .thenComparing(Client::getFirstName)
        );

        FilterConfig filterConfig = new FilterConfig();
        filterConfig.setLastRecords(5);

        List<Client> lastClients = clientRepository.getFilteredClients(filterConfig);

        assertEquals(5, lastClients.size());
        int sortedSize = sortedTestData.size();
        for (int i = 0; i < 5; i++) {
            assertEquals(sortedTestData.get(sortedSize - 5 + i), lastClients.get(i));
        }
    }

    @Test
    void testGetTopFiveClients1() {
        FilterConfig filterConfig = new FilterConfig();
        filterConfig.setTopRecords(5);

        List<Client> topClients = clientRepository.getFilteredClients(filterConfig);

        assertEquals(5, topClients.size());
    }
}