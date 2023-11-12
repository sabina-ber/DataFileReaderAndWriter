package org.berezinasabina;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {

    public static LocalDate parseDate(String dateString) {
        DateTimeFormatter[] formatters = new DateTimeFormatter[]{
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("yyyy-dd-MM"),
                DateTimeFormatter.ofPattern("yyyy.MM.dd"),
                DateTimeFormatter.ofPattern("yyyy.dd.MM"),
                DateTimeFormatter.ofPattern("dd.MM.yyyy"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                DateTimeFormatter.ofPattern("dd MM yyyy")
        };

        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(dateString.trim(), formatter);
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new DateTimeParseException("Could not parse the date: " + dateString, dateString, 0);
    }
}

