package org.berezinasabina;


import java.io.IOException;
import java.util.List;

public interface DataWriter {
     void writeData(List<Client> data, String filePath) throws IOException;

}
