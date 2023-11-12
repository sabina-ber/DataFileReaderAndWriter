package org.berezinasabina;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {

    @Test
    void parseValidDate() {
        String validDate = "2020-01-01";
        LocalDate expected = LocalDate.of(2020, 1, 1);
        LocalDate actual = DateUtil.parseDate(validDate);
        assertEquals(expected, actual, "The date should be correctly parsed.");
    }

    @Test
    void parseInvalidDateFormat() {
        String invalidDate = "01-2020-01";
        assertThrows(DateTimeParseException.class, () -> DateUtil.parseDate(invalidDate),
                "Should throw DateTimeParseException for invalid date format.");
    }

    @Test
    void parseDateFromFuture() {
        String futureDate = "3000-01-01";
        assertThrows(DateTimeParseException.class, () -> DateUtil.parseDate(futureDate),
                "Should throw DateTimeParseException for date from the future.");
    }

    @Test
    void parseDateBefore1900() {
        String oldDate = "1899-12-31";
        assertThrows(DateTimeParseException.class, () -> DateUtil.parseDate(oldDate),
                "Should throw DateTimeParseException for date before 1900.");
    }

    @Test
    void parseDate_DateFromFuture_ThrowsException() {
        String futureDate = "3000-01-01"; // Дата из будущего
        assertThrows(DateTimeParseException.class, () -> {
            DateUtil.parseDate(futureDate);
        });
    }

}
