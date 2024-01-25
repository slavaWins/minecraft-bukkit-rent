package org.destplay.renttable.helpers;

import java.util.Date;

public class DateHelper {
    public static String calculateDateDifference(Date startDate, Date endDate) {
        long milliseconds = endDate.getTime() - startDate.getTime();
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        seconds %= 60;
        minutes %= 60;
        hours %= 24;

        StringBuilder result = new StringBuilder();

        if (days > 0) {
            result.append(days).append(" Дней ");
        }
        if (hours > 0) {
            result.append(hours).append(" часа ");
        }
        if (minutes > 0) {
            result.append(minutes).append(" мин ");
        }
        if (seconds > 0) {
            result.append(seconds).append(" сек ");
        }

        return result.toString();
    }
}
