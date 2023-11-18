package com.project.web.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeFormattingUtil {
    private TimeFormattingUtil() {
    }

    public static String localDateTimeFormattingAll(LocalDateTime time) {
        if(time == null){
            return null;
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd HH:mm:ss");
        return time.format(formatter);
    }

    public static String localDateTimeFormatting(LocalDateTime time) {
        if(time == null){
            return null;
        }

        LocalDateTime now = LocalDateTime.now();

        if (time.toLocalDate().isEqual(now.toLocalDate())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return time.format(formatter);
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd");
            return time.format(formatter);
        }
    }
}
