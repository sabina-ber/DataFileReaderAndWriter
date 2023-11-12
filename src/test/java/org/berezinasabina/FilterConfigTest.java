package org.berezinasabina;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilterConfigTest {

    @Test
    void testFilterConfig() {
        FilterConfig config = new FilterConfig();

        String inputPath = "input/path";
        config.setInputFilePath(inputPath);
        assertEquals(inputPath, config.getInputFilePath());

        String outputPath = "output/path";
        config.setOutputFilePath(outputPath);
        assertEquals(outputPath, config.getOutputFilePath());

        Integer topRecords = 10;
        config.setTopRecords(topRecords);
        assertEquals(topRecords, config.getTopRecords());

        Integer lastRecords = 5;
        config.setLastRecords(lastRecords);
        assertEquals(lastRecords, config.getLastRecords());

        config.setMalesOnly(true);
        assertTrue(config.getMalesOnly());
        config.setMalesOnly(false);
        assertFalse(config.getMalesOnly());

        config.setFemalesOnly(true);
        assertTrue(config.getFemalesOnly());
        config.setFemalesOnly(false);
        assertFalse(config.getFemalesOnly());

        String nameFilter = "John";
        config.setNameFilter(nameFilter);
        assertEquals(nameFilter, config.getNameFilter());

        String lastNameFilter = "Doe";
        config.setLastNameFilter(lastNameFilter);
        assertEquals(lastNameFilter, config.getLastNameFilter());
    }
}
