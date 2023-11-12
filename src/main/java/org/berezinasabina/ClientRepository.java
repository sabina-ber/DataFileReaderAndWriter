package org.berezinasabina;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClientRepository {
    private final List<Client> clients = new ArrayList<>();


    public void loadData(String filePath) throws IOException {
        DataReader dataReader = DataReaderFactory.getDataReader(filePath);
        List<Client> data = dataReader.readData(filePath);
        clients.addAll(data);
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public List<Client> getAllClients() {
        return clients;
    }

    public List<Client> getFilteredClients(FilterConfig filterConfig) {
        Stream<Client> clientStream = clients.stream();

        if (filterConfig.getMalesOnly() != null && filterConfig.getMalesOnly()) {
            clientStream = clientStream.filter(client -> "M".equalsIgnoreCase(client.getGender()));
        } else if (filterConfig.getFemalesOnly() != null && filterConfig.getFemalesOnly()) {
            clientStream = clientStream.filter(client -> "F".equalsIgnoreCase(client.getGender()));
        }

        if (filterConfig.getNameFilter() != null && !filterConfig.getNameFilter().isEmpty()) {
            clientStream = clientStream.filter(client -> client.getFirstName().equalsIgnoreCase(filterConfig.getNameFilter()));
        }

        if (filterConfig.getLastNameFilter() != null && !filterConfig.getLastNameFilter().isEmpty()) {
            clientStream = clientStream.filter(client -> client.getLastName().equalsIgnoreCase(filterConfig.getLastNameFilter()));
        }

        clientStream = clientStream.sorted(
                Comparator.comparing(Client::getBirthDate)
                        .thenComparing(Client::getLastName)
                        .thenComparing(Client::getFirstName)
        );

        if (filterConfig.getTopRecords() != null) {
            clientStream = clientStream.limit(filterConfig.getTopRecords());
        } else if (filterConfig.getLastRecords() != null) {
            List<Client> sortedClients = clientStream.collect(Collectors.toList());
            return sortedClients.subList(Math.max(0, sortedClients.size() - filterConfig.getLastRecords()), sortedClients.size());
        }

        return clientStream.collect(Collectors.toList());
    }
}