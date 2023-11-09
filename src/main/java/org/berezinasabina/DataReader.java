package org.berezinasabina;

import java.io.IOException;
import java.util.List;

public interface DataReader {
    List<Client> readData(String filePath) throws IOException;
}
