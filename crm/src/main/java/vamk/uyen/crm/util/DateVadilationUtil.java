package vamk.uyen.crm.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateVadilationUtil {
    public static void validateDate(String startDate, String endDate) {
        if (startDate != null && endDate != null) {
            LocalDate parsedStartDate = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
            LocalDate parsedEndDate = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);

            if (parsedEndDate.isBefore(parsedStartDate)) {
                throw new IllegalArgumentException("End date must be later than start date");
            }
        }
    }
}
