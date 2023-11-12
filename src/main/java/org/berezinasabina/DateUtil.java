package org.berezinasabina;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {

    public static LocalDate parseDate(String dateString) throws DateTimeParseException {
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

        LocalDate parsedDate = null;

        for (DateTimeFormatter formatter : formatters) {
            try {
                parsedDate = LocalDate.parse(dateString.trim(), formatter);
                break;
            } catch (DateTimeParseException ignored) {
            }
        }

        if (parsedDate == null) {
            throw new DateTimeParseException("Could not parse the date: " + dateString, dateString, 0);
        }

        if (parsedDate.isAfter(LocalDate.now()) || parsedDate.getYear() < 1900) {
            throw new DateTimeParseException("Date " + dateString + " is not valid. Year must be from 1900 and not from the future.", dateString, 0);
        }

        return parsedDate;
    }
}

