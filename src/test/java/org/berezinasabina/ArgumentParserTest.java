package org.berezinasabina;

import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArgumentParserTest {

    @Test
    void parseValidArguments() throws ParseException {
        String[] args = {
                "--in", "input.csv",
                "--out", "output.csv",
                "--top", "5",
                "--males-only", "true"
        };
        ArgumentParser parser = new ArgumentParser(args);
        FilterConfig config = parser.parse();

        assertNotNull(config);
        assertEquals("input.csv", config.getInputFilePath());
        assertEquals("output.csv", config.getOutputFilePath());
        assertEquals(5, config.getTopRecords());
        assertTrue(config.getMalesOnly());
        assertNull(config.getFemalesOnly());
    }

    @Test
    void parseWithMissingRequiredArguments() {
        String[] args = { "--top", "5" };
        ArgumentParser parser = new ArgumentParser(args);

        assertThrows(ParseException.class, parser::parse);
    }

    @Test
    void parseWithInvalidNumberFormat() {
        String[] args = {
                "--in", "input.csv",
                "--out", "output.csv",
                "--top", "notANumber"
        };
        ArgumentParser parser = new ArgumentParser(args);

        assertThrows(IllegalArgumentException.class, parser::parse);
    }

    @Test
    void parseWithInvalidBooleanValue() {
        String[] args = {
                "--in", "input.csv",
                "--out", "output.csv",
                "--males-only", "notABoolean"
        };
        ArgumentParser parser = new ArgumentParser(args);

        assertThrows(IllegalArgumentException.class, parser::parse);
    }

    @Test
    void parseWithNameAndSurnameFilters() throws ParseException {
        String[] args = {
                "--in", "input.csv",
                "--out", "output.csv",
                "--name", "John",
                "--surname", "Doe"
        };
        ArgumentParser parser = new ArgumentParser(args);
        FilterConfig config = parser.parse();

        assertNotNull(config);
        assertEquals("John", config.getNameFilter());
        assertEquals("Doe", config.getLastNameFilter());
    }

    @Test
    void parseWithAllFilters() throws ParseException {
        String[] args = {
                "--in", "input.csv",
                "--out", "output.csv",
                "--top", "10",
                "--males-only", "true",
                "--name", "John",
                "--surname", "Doe"
        };
        ArgumentParser parser = new ArgumentParser(args);
        FilterConfig config = parser.parse();

        assertNotNull(config);
        assertEquals(10, config.getTopRecords());
        assertTrue(config.getMalesOnly());
        assertEquals("John", config.getNameFilter());
        assertEquals("Doe", config.getLastNameFilter());
    }

    @Test
    void parseWithNoFilters() throws ParseException {
        String[] args = {
                "--in", "input.csv",
                "--out", "output.csv"
        };
        ArgumentParser parser = new ArgumentParser(args);
        FilterConfig config = parser.parse();

        assertNotNull(config);
        assertNull(config.getTopRecords());
        assertNull(config.getMalesOnly());
        assertNull(config.getNameFilter());
        assertNull(config.getLastNameFilter());
    }
}


