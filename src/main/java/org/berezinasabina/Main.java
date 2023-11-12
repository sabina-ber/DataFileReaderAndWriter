package org.berezinasabina;

import java.io.IOException;
import java.util.List;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;

public class Main {
    public static void main(String[] args) {
        ArgumentParser argParser = new ArgumentParser(args);
        FilterConfig filterConfig;

        try {
            filterConfig = argParser.parse();
        } catch (ParseException e) {
            System.err.println("Error in command line arguments: " + e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("utility-name", argParser.getOptions());
            return;
        }

        ClientRepository clientRepository = new ClientRepository();
        try {
            clientRepository.loadData(filterConfig.getInputFilePath());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        List<Client> filteredClients = clientRepository.getFilteredClients(filterConfig);

        try {
            DataWriter dataWriter = DataWriterFactory.getDataWriter(filterConfig.getOutputFilePath());
            dataWriter.writeData(filteredClients, filterConfig.getOutputFilePath());
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}

