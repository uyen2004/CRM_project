package vamk.uyen.crm.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateValidationUtil {
    public static boolean isDateValidate(String startDate, String endDate) {
        LocalDate parsedStartDate = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate parsedEndDate = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);

        if (parsedEndDate.isBefore(parsedStartDate)) {
            return false;
        }else{
            return true;
        }
    }
}