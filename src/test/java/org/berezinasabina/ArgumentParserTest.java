package org.berezinasabina;

import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArgumentParserTest {

    @Test
    void parseValidArguments() throws ParseException {
        String[] args = {
                "--in", "input.txt",
                "--out", "output.txt",
                "--top", "5",
                "--males-only", "true"
        };
        ArgumentParser parser = new ArgumentParser(args);
        FilterConfig config = parser.parse();

        assertNotNull(config);
        assertEquals("input.txt", config.getInputFilePath());
        assertEquals("output.txt", config.getOutputFilePath());
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
                "--in", "input.txt",
                "--out", "output.txt",
                "--top", "notANumber"
        };
        ArgumentParser parser = new ArgumentParser(args);

        assertThrows(IllegalArgumentException.class, parser::parse);
    }

    @Test
    void parseWithInvalidBooleanValue() {
        String[] args = {
                "--in", "input.txt",
                "--out", "output.txt",
                "--males-only", "notABoolean"
        };
        ArgumentParser parser = new ArgumentParser(args);

        assertThrows(IllegalArgumentException.class, parser::parse);
    }
}

