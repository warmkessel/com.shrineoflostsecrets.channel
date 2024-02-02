package com.shrineoflostsecrets.channel.util;

import com.google.cloud.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateFormatter {

    /**
     * Converts a Google Cloud Timestamp to a string representing the hour and minute.
     *
     * @param timestamp The timestamp to convert.
     * @return A string formatted as HH:mm representing the hour and minute.
     */
    public static String convertToHourAndMin(Timestamp timestamp) {
        // Convert Timestamp to LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.ofInstant(timestamp.toDate().toInstant(), ZoneId.systemDefault());

        // Define a formatter to extract hours and minutes
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        // Format the LocalDateTime to a string containing only hours and minutes
        return localDateTime.format(formatter);
    }
}
