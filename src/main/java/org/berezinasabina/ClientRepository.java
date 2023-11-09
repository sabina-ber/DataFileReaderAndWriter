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
        if (filterConfig.getTopRecords() != null && filterConfig.getLastRecords() != null) {
            throw new IllegalArgumentException("Cannot use --top and --last together. Please specify only one.");
        }
        if (filterConfig.getFemalesOnly() != null && filterConfig.getMalesOnly() != null
            && filterConfig.getFemalesOnly() && filterConfig.getMalesOnly()) {
            throw new IllegalArgumentException("Cannot use --males-only and --females-only" +
                    " together. Please specify only one.");
        }

        if (Boolean.TRUE.equals(filterConfig.getMalesOnly())) {
            clientStream = clientStream.filter(client -> "M".equalsIgnoreCase(client.getGender()));
        } else if (Boolean.TRUE.equals(filterConfig.getFemalesOnly())) {
            clientStream = clientStream.filter(client -> "F".equalsIgnoreCase(client.getGender()));
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