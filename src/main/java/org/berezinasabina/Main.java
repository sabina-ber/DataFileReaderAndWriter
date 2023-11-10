package org.berezinasabina;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            ArgumentParser argParser = new ArgumentParser(args);
            FilterConfig filterConfig = argParser.parse();

            ClientRepository clientRepository = new ClientRepository();
            clientRepository.loadData(filterConfig.getInputFilePath());

            List<Client> filteredClients = clientRepository.getFilteredClients(filterConfig);
            DataWriter dataWriter = DataWriterFactory.getDataWriter(filterConfig.getOutputFilePath());
            dataWriter.writeData(filteredClients, filterConfig.getOutputFilePath());

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("An error occurred while reading or writing the CSV file.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }

}